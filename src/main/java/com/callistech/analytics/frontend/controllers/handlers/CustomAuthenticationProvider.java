//package com.callistech.analytics.frontend.controllers.handlers;
//
//import java.security.MessageDigest;
//import java.util.ArrayList;
//import java.util.List;
//import javax.ejb.EJB;
//
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    public static final String PROFILE_NAME_ADMINISTRATOR = "Administrator";
//
////    @EJB(mappedName = "ejb/UserControlModelEJB")
////    private UserControlModelI userControlModel;
////
////    @EJB(mappedName = "ejb/TACACsManagerEJB")
////    private TACACsManagerI tacacsManager;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        String password = (String) authentication.getCredentials();
//        String passwordMD5 = getPasswordMD5(password);
//
////        TacacsServerAuthType authType = tacacsManager.getTacacsServerAuthType();
////
////        if (userControlModel.isSuperUser(username, password)) {
////            return novalidation(username, passwordMD5, authentication, password);
////        } else if (authType == null || authType.getAuthType().equals("LOCAL") || username.equalsIgnoreCase("admin") || username.equalsIgnoreCase("cablevision")) {
////            return validationLocal(username, passwordMD5, authentication, password);
////        } else if (authType.getAuthType().equals("TACACS")) {
////            try {
////                return validationTACACs(username, password, authentication);
////            } catch (BadCredentialsException ex) {
////                //Si fallo TACACs probar localmente
//                return validationLocal(username, passwordMD5, authentication, password);
////            }
////        } else {
////            throw new BadCredentialsException("Wrong authenticator.");
////        }
//    }
//
//    private Authentication validationTACACs(String username, String password, Authentication authentication) throws BadCredentialsException {
//        if (tacacsManager.authenticateUser(username, password)) {
//            Profile profile = userControlModel.getProfileByTACACsUser(username);
//
//            validateACLIPs(profile, authentication);
//
//            org.springframework.security.core.userdetails.User cUser = getUserSpring(username, password, profile.getProfileName());
//
//            return new UsernamePasswordAuthenticationToken(cUser, password, cUser.getAuthorities());
//        } else {
//            throw new BadCredentialsException("Invalid username or password.");
//        }
//    }
//
//    private Authentication validationLocal(String username, String passwordMD5, Authentication authentication, String password) throws BadCredentialsException {
//        User user = userControlModel.getUserByName(username);
//
//        if (user == null || !user.getUsername().equalsIgnoreCase(username)) {
//            throw new BadCredentialsException("Username not found.");
//        }
//
//        if (!passwordMD5.equals(user.getPassword())) {
//            throw new BadCredentialsException("Wrong password.");
//        }
//
//        validateACLIPs(user.getProfileId(), authentication);
//
//        org.springframework.security.core.userdetails.User cUser = getUserSpring(user.getUsername(), user.getPassword(), user.getProfileId().getProfileName());
//
//        return new UsernamePasswordAuthenticationToken(cUser, password, cUser.getAuthorities());
//    }
//
//    private void validateACLIPs(Profile profile, Authentication authentication) throws BadCredentialsException {
//        if (profile.isAclip()) {
//            Object details = authentication.getDetails();
//            String remoteAddress = null;
//            if (details instanceof WebAuthenticationDetails) {
//                remoteAddress = ((WebAuthenticationDetails) details).getRemoteAddress();
//            }
//            if (!validIP(remoteAddress, profile.getIps())) {
//                throw new BadCredentialsException("Invalid IP Address " + remoteAddress + ".");
//            }
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> arg0) {
//        return true;
//    }
//
//    private String getPasswordMD5(String original) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(original.getBytes());
//            byte[] digest = md.digest();
//            StringBuilder sb = new StringBuilder();
//            for (byte b : digest) {
//                sb.append(String.format("%02x", b & 0xff));
//            }
//            return sb.toString();
//        } catch (Exception ex) {
//        }
//        return null;
//    }
//
//    private org.springframework.security.core.userdetails.User getUserSpring(String username, String password, String profileName) {
//        Role role = new Role();
//        role.setName(profileName);
//        List<Role> roles = new ArrayList<Role>();
//        roles.add(role);
//        org.springframework.security.core.userdetails.User cc = new org.springframework.security.core.userdetails.User(username, password, roles);
//        return cc;
//    }
//
//    private static boolean validIP(String remoteAddress, List<String> ips) {
//        if (remoteAddress == null || ips == null) {
//            return false;
//        }
//        for (String ip : ips) {
//            if (IPAddressUtils.isIPAddressValid(ip, true)) {
//                if (ip.contains("/")) {
//                    //TIENE MASCARA
//                    Long[] limits = IPAddressUtils.getIpEnd(ip);
//                    Long ipLong = IPAddressUtils.convertIPAddressToLong(remoteAddress);
//                    if (ipLong != null && limits != null && ipLong >= limits[0] && ipLong <= limits[1]) {
//                        return true;
//                    }
//                } else //NO TIENE MASCARA
//                {
//                    if (IPAddressUtils.isIPAddressValid(ip, false) && ip.equals(remoteAddress)) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    private Authentication novalidation(String username, String passwordMD5, Authentication authentication, String password) {
//        org.springframework.security.core.userdetails.User cUser = getUserSpring(username, password, PROFILE_NAME_ADMINISTRATOR);
//
//        return new UsernamePasswordAuthenticationToken(cUser, password, cUser.getAuthorities());
//    }
//
//}
