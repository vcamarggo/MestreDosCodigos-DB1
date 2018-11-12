package br.com.poc.otp.otp;

public class Token {
    private final String currentToken;
    private final String nextToken;

    public Token(String currentToken, String nextToken) {
        this.currentToken = currentToken;
        this.nextToken = nextToken;
    }

    public boolean isValid(String tokenToValidate) {
        return tokenToValidate.equals(currentToken) || tokenToValidate.equals(nextToken);//TODO verificar condicao por atraso de rede
    }

    public String getCurrentToken() {
        return currentToken;
    }

    public String getNextToken() {
        return nextToken;
    }
}
