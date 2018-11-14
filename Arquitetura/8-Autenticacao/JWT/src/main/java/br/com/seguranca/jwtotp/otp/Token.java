package br.com.seguranca.jwtotp.otp;

public class Token {
    private final String currentToken;
    private final String nextToken;

    Token(String currentToken, String nextToken) {
        System.out.println("Current " + currentToken);
        System.out.println("Next " + nextToken);
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
