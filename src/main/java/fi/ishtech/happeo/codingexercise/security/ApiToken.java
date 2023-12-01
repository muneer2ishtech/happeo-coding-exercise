package fi.ishtech.happeo.codingexercise.security;

import java.security.SecureRandom;
import java.util.zip.CRC32;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Muneer Ahmed Syed
 */
@Slf4j
public class ApiToken {

	public String createToken(String crc32SecretKey) {
		var secureRandom = new SecureRandom();
		
		var crc32 = new CRC32()
           .update(crc32SecretKey.toByteArray())
           .update(randomString.toByteArray())
        
	}
	
}
