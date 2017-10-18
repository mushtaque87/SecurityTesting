package com.philips.utilities;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.io.BaseEncoding;
import java.io.PrintStream;
import java.net.URI;
import java.util.Map;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignatureGenerator {

	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	private static final Joiner SIGN_DATA_JOINER = Joiner.on('|');
	private static final BaseEncoding HEX_ENCODER = BaseEncoding.base16().lowerCase();

	private static final Splitter.MapSplitter PARAMETER_SPLITTER = Splitter.on('&').withKeyValueSeparator('=');
	private static final Joiner.MapJoiner PARAMETER_JOINER = Joiner.on('|').withKeyValueSeparator("=");

	private final SecretKeySpec secretKeySpec;

	public SignatureGenerator(String sharedSecret) {
		if (sharedSecret == null)
			throw new IllegalArgumentException("Missing API signing shared secret");

		this.secretKeySpec = generateSecretKeySpec(sharedSecret);
	}

	public SignatureGenerator() {
		this.secretKeySpec = null;
	}

	/**
	 * Generate a signature to authenticate private API calls in Mooncore.
	 *
	 * @param requestMethod
	 *            requestMethod of the call to be signed.
	 * @param requestUrl
	 *            requestUrl of call to be signed. Can be full URL or path
	 * @return valid signature.
	 */
	public String generate(String requestMethod, String requestUrl) {
		return generate(requestMethod, requestUrl, null);
	}

	/**
	 * Generate a signature to authenticate private API calls in Mooncore.
	 *
	 * @param requestMethod
	 *            requestMethod of the call to be signed.
	 * @param requestUrl
	 *            requestUrl of call to be signed. Can be full URL or path
	 * @param sharedSecret
	 *            secret to sign the request with, if different from the default
	 * @return valid signature.
	 */
	public String generate(String requestMethod, String requestUrl, String sharedSecret) {
		SecretKeySpec keySpec;
		if (sharedSecret != null)
			keySpec = generateSecretKeySpec(sharedSecret);
		else if (secretKeySpec != null)
			keySpec = secretKeySpec;
		else
			throw new IllegalStateException("No API signing shared secret available");

		try {
			String signingData = generateSigningData(requestMethod.toUpperCase(), requestUrl);
			return generateSignature(signingData, keySpec);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Unable to sign request, most likely due to invalid or unsupported url", ex);
		}
	}

	/**
	 * @return request path|request verb|param1=value1|param2=value2
	 */
	private String generateSigningData(String requestMethod, String requestUrl) {
		URI uri = URI.create(requestUrl);

		String path = uri.getPath();
		String parameters = extractSortedParameters(uri);

		return SIGN_DATA_JOINER.join(path, requestMethod, parameters);
	}

	/**
	 * Extract parameters, sort alphabetically, and separate with '|'.
	 *
	 * E.g. paramB=value&paramA=value -> paramA=value|paramB=value
	 */
	private String extractSortedParameters(URI uri) {
		String parameters = uri.getQuery();

		if (parameters == null)
			return "";

		Map<String, String> parameterMap = new TreeMap<>(PARAMETER_SPLITTER.split(parameters));
		return PARAMETER_JOINER.join(parameterMap);
	}

	private String generateSignature(String data, SecretKeySpec keySpec) {
		byte[] hmacBytes = generateMAC(data.getBytes(), keySpec);
		return HEX_ENCODER.encode(hmacBytes);
	}

	private byte[] generateMAC(byte[] data, SecretKeySpec keySpec) {
		return getHmacInstance(keySpec).doFinal(data);
	}

	private Mac getHmacInstance(SecretKeySpec keySpec) {
		try {
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(keySpec);

			return mac;
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to instantiate HMAC instance", e);
		}
	}

	private SecretKeySpec generateSecretKeySpec(String sharedSecret) {
		return new SecretKeySpec(sharedSecret.getBytes(), HMAC_SHA1_ALGORITHM);
	}

	// System.err, System.out and System.exit are used intentionally here.
	public static void main(String[] args) {
		if (args.length < 3) {
			System.err.println("Usage: cli [shared secret] [request method] [request url]");
			System.exit(1);
		}

		String secret = args[0];
		String requestMethod = args[1];
		String requestUrl = args[2];

		System.out.println(new SignatureGenerator(secret).generate(requestMethod, requestUrl));
	}

}
