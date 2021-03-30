package filler;

import dao.orm.OrmUserDao;
import entity.User;
import exception.dao.StoreException;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public final class UserTestDataFiller {

    private static final List<Character> allowedCharacters = allowedCharacters();
    private static final List<String> operatorDigits = Arrays.asList("097", "095", "066", "037", "050", "044");
    private static final List<String> emailDomains = allowedEmailDomains();
    private final OrmUserDao userDao;

    public UserTestDataFiller(OrmUserDao userDao) {
        this.userDao = userDao;
    }

    private static List<Character> allowedCharacters(){
        String allowed = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";
        char[] allowedChars = allowed.toCharArray();
        int[] codes = new int[allowedChars.length];
        for (int i = 0; i < allowedChars.length; i++) {
            codes[i] = allowedChars[i];
        }
        return Arrays.stream(codes)
                .mapToObj(code -> (char) code)
                .collect(Collectors.toList());
    }

    private static List<String> allowedEmailDomains(){
        return Arrays.asList("gmail.com", "ukr.net", "hotmail.com", "knu.edu", "live.com", "mail.ru");
    }

    public void fillTable() throws StoreException {
        Collection<User> randomUsers = randomUsers(300);
        for (User u : randomUsers) {
            userDao.save(u);
        }
    }

    private Collection<User> randomUsers(int amount) {
        Collection<User> out = new HashSet<>();
        Random random = ThreadLocalRandom.current();
        for (int i = 0; i < amount; i++) {
            User u = new User();
            String name = randomName(random.nextInt(21) + 4, random);
            while(nameExists(out, name)){
                name = randomName(random.nextInt(25), random);
            }
            String email = name.concat("@")
                    .concat(emailDomains.get(random.nextInt(emailDomains.size())));

            u.setName(name);
            u.setEmail(email);
            u.setComments(new LinkedList<>());
            u.setPassword(UUID.randomUUID().toString());
            u.setItems(new ArrayList<>());
            String phoneNumber = randomPhoneNumber(random);
            while (phoneNumberExists(out, phoneNumber)){
                phoneNumber = randomPhoneNumber(random);
            }
            u.setPhoneNumber(phoneNumber);
            out.add(u);
        }
        return out;
    }

    private boolean phoneNumberExists(Collection<User> users, String phoneNumber){
        return users.stream().map(User::getPhoneNumber).anyMatch(n -> Objects.equals(phoneNumber, n));
    }

    private static String randomPhoneNumber(Random random){
        StringBuilder out = new StringBuilder(10);
        out.append(operatorDigits.get(random.nextInt(operatorDigits.size())));
        while(out.length() < 10){
            out.append(random.nextInt(10));
        }
        return out.toString();
    }

    private boolean nameExists(Collection<User> users, String name){
        return users.stream().map(User::getName).anyMatch(n -> Objects.equals(name, n));
    }

    private String randomName(int length, Random random) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(allowedCharacters.get(random.nextInt(allowedCharacters.size())));
        }
        return sb.toString();
    }
}
