package com.dennisjonsson.tm.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTToken {

    private static final String test_secret = "test_app_key";

    public static final String ID = "id";
    public static final String SUBJECT = "sbject";
    public static final String ISSUER = "issuer";
    public static final String EXPIRATION = "expiration";

    // com.nimbus.jose
    /*
     * public String createJWT(){ RsaJsonWebKey rsaJsonWebKey =
     * RsaKeyProducer.produce();
     * 
     * JwtClaims claims = new JwtClaims(); claims.setSubject("user1");
     * 
     * JsonWebSignature jws = new JsonWebSignature();
     * jws.setPayload(claims.toJson());
     * jws.setKey(rsaJsonWebKey.getPrivateKey());
     * jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
     * 
     * String jwt = jws.getCompactSerialization() }
     */

    public static String createWebToken(int id, String issuer, String subject) {
	// The JWT signature algorithm we will be using to sign the token
	SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	long nowMillis = System.currentTimeMillis();
	Date now = new Date(nowMillis);

	// We will sign our JWT with our ApiKey secret
	byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(test_secret);
	Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

	// Let's set the JWT Claims
	JwtBuilder builder = Jwts.builder().setId(String.valueOf(id)).setIssuedAt(now).setSubject(subject)
		.setIssuer(issuer).signWith(signatureAlgorithm, signingKey);

	// if it has been specified, let's add the expiration

	// if (ttlMillis >= 0) {
	// long expMillis = nowMillis + ttlMillis;
	// Date exp = new Date(expMillis);
	// builder.setExpiration(exp);
	// }

	// Builds the JWT and serializes it to a compact, URL-safe string
	return builder.compact();
    }

    /**
     * Return HashMap of JWT content. see static fields in JWTToken.
     * 
     * @param jwt
     * @return HashMap<String, String> result
     */
    public static HashMap<String, Object> parseJWT(String jwt) {

	HashMap<String, Object> result = new HashMap<>();
	// This line will throw an exception if it is not a signed JWS (as
	// expected)
	Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(test_secret))
		.parseClaimsJws(jwt).getBody();

	result.put(ID, claims.getId());
	result.put(SUBJECT, claims.getSubject());
	result.put(ISSUER, claims.getIssuer());
	result.put(EXPIRATION, claims.getExpiration());

	return result;

    }

}
