package de.myfdweb.woc.mcplugins.mcauth;

import java.util.ArrayList;

public class Token {

    private static final String dictionary = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static ArrayList<Token> tokens = new ArrayList<>();
    private String token, uuid;
    private long validTill;

    public Token(String uuid) {
        do {
            this.token = "";
            for(int i = 0; i<6; i++)
                this.token += dictionary.charAt((int) (Math.random()*dictionary.length()));
        } while(getByToken(this.token) != null);
        this.uuid = uuid;
        this.validTill = System.currentTimeMillis() + 3 * 60 * 1000;
        tokens.add(this);
    }

    public static Token getByToken(String token) {
        for (Token t : tokens)
            if (t.getToken().equals(token))
                return t;
        return null;
    }

    public static void cleanup() {
        for(Token t : tokens)
            t.remove();
    }

    public String getToken() {
        return token;
    }

    public String getUUID() {
        return uuid;
    }

    public boolean isValid() {
        return System.currentTimeMillis() < this.validTill;
    }

    public void remove() {
        tokens.remove(this);
    }
}
