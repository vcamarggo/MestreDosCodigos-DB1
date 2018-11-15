package br.com.poc.otp.otp;
/**
 * Created by vinicius.camargo on 04/10/2018
 */
public class Token {
    private final String currentToken;
    private final String nextToken;

    public Token(String currentToken, String nextToken) {
        this.currentToken = currentToken;
        this.nextToken = nextToken;
    }

    public boolean isValid(String tokenToValidate) {
        return tokenToValidate.equals(currentToken) || tokenToValidate.equals(nextToken);
    }

    public String getCurrentToken() {
        return currentToken;
    }

    public String getNextToken() {
        return nextToken;
    }
}
