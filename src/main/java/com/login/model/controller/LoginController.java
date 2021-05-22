package com.login.model.controller;

import com.login.model.bean.LoginBean;
import com.login.model.bean.LoginResponse;
import com.login.model.bean.UserBean;
import com.login.model.security.TokenGenerators;
import com.login.model.service.UserService;
import com.login.model.utils.Constants;
import com.login.model.utils.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth/")
@Slf4j
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TokenGenerators jwtTokenGenerator;

    private String publicKeysText;
    private String privateKeysText;
    private static final String PADDING_TEXT = "RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING";

    public LoginController() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(4096);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            //Converting PublicKey and PrivateKey to String
            publicKeysText = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            privateKeysText = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/getEncryptedText")
    public String encrypt(@RequestParam(name="publicKey",required = true) String publicKeys,
                          @RequestParam(name="planeText",required = true) String planeText) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeys));
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);

        Cipher cipher = Cipher.getInstance(PADDING_TEXT);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherText = cipher.doFinal(planeText.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }


    public String decrypt(String privateKeysText, String encryptedText) throws InvalidKeySpecException,NoSuchAlgorithmException,NoSuchPaddingException,InvalidKeyException,IllegalBlockSizeException,BadPaddingException{
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeysText));
        PrivateKey privateKey = null;
        privateKey = keyFactory.generatePrivate(keySpec);
        Cipher cipher = Cipher.getInstance(PADDING_TEXT);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedTextArray = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedTextArray);
    }


    @GetMapping("/getPublicKey")
    public ResponseEntity<LoginResponse<Model>> getPublicKey() {
        Model model = new Model();
        model.addAttribute("publicKey", this.publicKeysText);
        return new ResponseEntity<>(new LoginResponse<>(model), HttpStatus.OK);
    }

    @PostMapping(value = "login")
    public ResponseEntity<LoginResponse<Model>> loginUser(@RequestBody LoginBean loginBean) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        Model model = new Model();
        if (loginBean != null) {
            UserBean userBean = userService.getUserByEmail(loginBean.getUserEmail());
            if (userBean.getUserId() == null) {
                model.addAttribute(Constants.ERROR, "User Not Found");
                return new ResponseEntity<>(new LoginResponse<>(model), HttpStatus.NOT_FOUND);
            }
            String password = null;
            String token = jwtTokenGenerator.getJWTToken(userBean.getUserId(), userBean.getUserName(), userBean.getUserEmail());
            try {
                password = decrypt(privateKeysText, loginBean.getPassword());
            }catch (Exception e) {
                model.addAttribute("error", "Wrong Username and Password");
                return new ResponseEntity<>(new LoginResponse<>(model), HttpStatus.BAD_REQUEST);
            }
            if (bCryptPasswordEncoder.matches(password, userBean.getPassword())) {
                model.addAttribute("userId", userBean.getUserId());
                model.addAttribute("userName", userBean.getUserName());
                model.addAttribute("userEmail", userBean.getUserEmail());
                model.addAttribute("isActive", userBean.getIsActive());
                model.addAttribute("token", token);
            } else {
                model.addAttribute("error", "Wrong Username and Password");
                return new ResponseEntity<>(new LoginResponse<>(model), HttpStatus.BAD_REQUEST);
            }
        } else {
            model.addAttribute("error", "Invalid user information");
            return new ResponseEntity<>(new LoginResponse<>(model), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new LoginResponse<>(model), HttpStatus.OK);
    }
}