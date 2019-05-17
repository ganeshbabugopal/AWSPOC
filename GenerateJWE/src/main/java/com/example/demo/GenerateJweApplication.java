package com.example.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;

@SpringBootApplication
public class GenerateJweApplication {

	public static void main(String[] args) {
	//	SpringApplication.run(GenerateJweApplication.class, args);
	GenerateJweApplication genJweApp = new GenerateJweApplication();
	String encryptedJWT = genJweApp.generateJWE(new HashMap<String,String>(){{this.put("key1","key1value");}});
	System.out.println(" encrypted JWT " + encryptedJWT);
	String payload = genJweApp.decryptJWT(encryptedJWT);
	}

	
	public String decryptJWT(String encryptedJWTString) {
		try {
			EncryptedJWT jwt = EncryptedJWT.parse(encryptedJWTString);
			
			RSAPrivateKey privateKey = getPrivateKey("/Users/crsmobiledev/private_key.pem");
			RSADecrypter decrypter = new RSADecrypter(privateKey);
			jwt.decrypt(decrypter);

			System.out.println(jwt.getJWTClaimsSet().getIssuer());;
			System.out.println(jwt.getJWTClaimsSet().getSubject());
			System.out.println(jwt.getJWTClaimsSet().getAudience().size());
			System.out.println(jwt.getJWTClaimsSet().getExpirationTime());
			System.out.println(jwt.getJWTClaimsSet().getNotBeforeTime());
			System.out.println(jwt.getJWTClaimsSet().getIssueTime());
			System.out.println(jwt.getJWTClaimsSet().getJWTID());
			System.out.println(jwt.getJWTClaimsSet().getClaims());
			
			
			
		} catch (ParseException| GeneralSecurityException | IOException | JOSEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		return null;
	}
	
	
	public String generateJWE(Map<String,String> data)
	{
		
		String privatekey = "MIIEpQIBAAKCAQEAwzWH3m3+iIop2Y2CxBWzoyNnDhRbhxnIP8RJXIqvmj3aGc6JvGh01W/qHMpoieMa6Nb4U/m71PFLyvjnF1n4t8FDmDyY0Rc82JLO9RQqEcWOVe0/SONtOaMa1JXvgVeQDytO2UNznkgCorz1zlgbWVv/wcUlXJ9WLg/HFd+uMeThYakCALmqUZ3XLg3ebCl5UJ9m3qK24KCHJkuGaErDwq62NJzWL/ZjQ2ar51kOz1fBHd5GbIE3uPCEhAVFn1DWUaqfaMTq59IYh6Z1dNHOjk/scrE3iJe/Bxa2F8KZpLWQRcXSUSKqOxUepicQi9yr/GrbqTl8nMjPbnCvG8WxQwIDAQABAoIBAQCr3BsMZsZqq0kA8acovvpyLB70tFOIc9hq24LC6I6FOuMjSUTWDRifOHJieojirIDqcSgbtof5h550ygGZ+2oPTSUXiHH5032MJmPyPJh1IPqZB9BpeWCWF9TtSDueIdsAn95BRTMrLLa1U6sMyWD6oRZgIBMuolEbKGTcCxuvbAlT+7LiwI/u4aj5QVvy3Bqb/bXNzlbI6Yp9O4tAO2RNwpC8Q0gCgu80PqXRYHyVPD6gAfzukZjn1zmUHARiP62M+ZG4YxlwwhNnZgJhJ8wVD4IOfkPUTcwEg0XIyVp5KoiUCZR8MX2dhBzpFwtLpOEiDZtfCf9kTMpHSVCB/cPRAoGBAPK5xKAT/raeC8xbVkToKbwtgZ+chABw6Ng4PrPU37rhROwJ3AMLdf/ATaos5MctKrobqiw5Lbds5lIAQ1+MwT+B3fx0oZXs8maar263bC5sN3EjjUiaRy9rtex2Owc8J5lqiEhEXrT51hsR82KuL2rZRiDvmkzfhIcGUjZGgRRvAoGBAM3ihEaHgme2Azgg7ebk5ESWF7WnorCeVsZ6PPOtFAieOdVjgERIyofPPo0uCvmHElgxnQxqwiaabinEoGpsherlmOh64gWIlyRmXrDhd9jHl/mwji/JdGIOcFVLmYAQYU26cTD3hY7xyFWBWqj27APr9oRqJF8NlSWqXRqomuJtAoGACS5ruJ5pASFjUtyVm8+oXHzgLVhvSdD7jco63PSNbfU9dSw+TUMuuHjetzQxhzM3LYkEW93h09FjuPBXnBKn8K4J5pXG0vP+i1EIl8iKaAsh4b61hmyHisypOgOS4ggte0kjVJB5gKD+r1FzgmUSWBlV5MMLdCL0UWM00ubB1TMCgYEAoWEOYeJR+00WFObq03NS4dfsWb0+rsZOKmEiabae32p21qaZoeyUpAN0emE+xPx1CqBJA7Si3nNqKl8vhnvuNIMnagL4K3I3VpbPXkI9GLTKkTMtIJP+H+ZFcvZBKbqtKqobk1BRZgxrBWrRT2PM9/37SyuPutoAsHdrZzwFu7kCgYEAiod7DwWBta4KIiRGNPfs8qXzSXkweCQjhiD1GnTpZLnFptQXMmNZF1GfWJ7LeOIApIZUqCRxuzl146YTuLgCLBAlm5GKzqpcRjw6auF/LRijQZI2z4N9rdUQsg9vTHlhCs72K5rqVvnLvqhcVJz+oap1twv3g2dRIoTu2t+PWDw=";
		
		String publickey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwzWH3m3+iIop2Y2CxBWzoyNnDhRbhxnIP8RJXIqvmj3aGc6JvGh01W/qHMpoieMa6Nb4U/m71PFLyvjnF1n4t8FDmDyY0Rc82JLO9RQqEcWOVe0/SONtOaMa1JXvgVeQDytO2UNznkgCorz1zlgbWVv/wcUlXJ9WLg/HFd+uMeThYakCALmqUZ3XLg3ebCl5UJ9m3qK24KCHJkuGaErDwq62NJzWL/ZjQ2ar51kOz1fBHd5GbIE3uPCEhAVFn1DWUaqfaMTq59IYh6Z1dNHOjk/scrE3iJe/Bxa2F8KZpLWQRcXSUSKqOxUepicQi9yr/GrbqTl8nMjPbnCvG8WxQwIDAQAB";
		
		Date now = new Date();

		JWTClaimsSet jwtClaims = new JWTClaimsSet.Builder()
		    .issuer("https://openid.net")
		    .subject("alice")
		    .audience(Arrays.asList("https://app-one.com", "https://app-two.com"))
		    .expirationTime(new Date(now.getTime() + 1000*60*10)) // expires in 10 minutes
		    .notBeforeTime(now)
		    .issueTime(now)
		    .claim("key1", "key1value")
		    .jwtID(UUID.randomUUID().toString())
		    .build();
		
		JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128CBC_HS256);

		// Create the encrypted JWT object
		EncryptedJWT jwt = new EncryptedJWT(header, jwtClaims);
		RSAEncrypter encrypter = null;
		String jwtString = null;
		
		try {
	//		RSAPrivateKey privateKey = getPrivateKey(" ");
			RSAPublicKey publicKey = getPublicKey("/Users/crsmobiledev/public.pem");
			 encrypter = new RSAEncrypter(publicKey);
			 jwt.encrypt(encrypter);
			 
			 jwtString = jwt.serialize();

			 System.out.println(" encrypted jwt " + jwtString);
		
		} catch (IOException | GeneralSecurityException | JOSEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return jwtString;
	}
	
	
	private static String getKey(String filename) throws IOException {
	    // Read key from file
	    String strKeyPEM = "";
	    BufferedReader br = new BufferedReader(new FileReader(filename));
	    String line;
	    while ((line = br.readLine()) != null) {
	        strKeyPEM += line + "\n";
	    }
	    br.close();
	    return strKeyPEM;
	}
	public static RSAPrivateKey getPrivateKey(String filename) throws IOException, GeneralSecurityException {
	    String privateKeyPEM = getKey(filename);
	    return getPrivateKeyFromString(privateKeyPEM);
	}

	public static RSAPrivateKey getPrivateKeyFromString(String key) throws IOException, GeneralSecurityException {
	    String privateKeyPEM = key;
	    privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----\n", "");
	    privateKeyPEM = privateKeyPEM.replace("-----END PRIVATE KEY-----", "");
	    byte[] encoded = Base64.decodeBase64(privateKeyPEM);
	    KeyFactory kf = KeyFactory.getInstance("RSA");
	    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
	    RSAPrivateKey privKey = (RSAPrivateKey) kf.generatePrivate(keySpec);
	    return privKey;
	}


	public static RSAPublicKey getPublicKey(String filename) throws IOException, GeneralSecurityException {
	    String publicKeyPEM = getKey(filename);
	    System.out.println(" public key "+ publicKeyPEM);
	    return getPublicKeyFromString(publicKeyPEM);
	}

	public static RSAPublicKey getPublicKeyFromString(String key) throws IOException, GeneralSecurityException {
	    String publicKeyPEM = key;
	    publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----\n", "");
	    publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");
	    byte[] encoded = Base64.decodeBase64(publicKeyPEM);
	    KeyFactory kf = KeyFactory.getInstance("RSA");
	    RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(encoded));
	    return pubKey;
	}
	
	
}
