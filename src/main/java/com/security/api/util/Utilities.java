package com.security.api.util;

import com.security.api.auth.base.User;
import javax.servlet.http.HttpServletRequest;

import com.security.api.entity.Customer;
import com.security.api.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component @RequiredArgsConstructor
public class Utilities {
    private final HttpServletRequest request;
    private final CustomerRepository customerRepository;

    public String getClassName(Object object) {
        String[] parts = object.getClass().getName().split("\\.");
        return parts[parts.length - 1];
    }

    public String getUUID() {
        return java.util.UUID.randomUUID().toString();
    }

    public GeneralResponse successResponse(String message, Object data) {
        return GeneralResponse.builder()
                .message(message)
                .data(data)
                .status("200")
                .comment("success")
                .build();
    }

    public GeneralResponse errorResponse(String message) {
        return GeneralResponse.builder()
                .message(message)
                .data(null)
                .status("400")
                .comment("error")
                .build();
    }

    public GeneralResponse exceptionResponse(String message, Exception e) {
        String cause = e.getCause().getCause().getMessage();
        cause = cause.substring(cause.indexOf("Detail:"));
        return GeneralResponse.builder()
                .message(message)
                .data(null)
                .status("400")
                .comment(cause)
                .build();
    }

    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User user) {
            return user;
        }
        return null;
    }

    public Boolean userLogguedHasRole(String role) {
        User user = getLoggedUser();

        if (user != null) {
            return user.getRoles().stream().anyMatch(r -> r.getName().equals(role));
        }
        return false;
    }

    public Boolean userHasRole(User user, String role) {
        if (user != null) {
            return user.getRoles().stream().anyMatch(r -> r.getName().equals(role));
        }
        return false;
    }

    public String getClientIp() {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        if (remoteAddr != null && remoteAddr.contains(",")) {
            remoteAddr = remoteAddr.split(",")[0];
        }

        return remoteAddr;
    }

    public Customer getCustomerLogged() {
        User user = getLoggedUser();
        if (user != null) {
            return customerRepository.findByPasiveIsFalseAndIdUser(user.getId());
        }
        return null;
    }

    public Integer getRealIdBusiness(Integer idBusiness) {
        if (Boolean.FALSE.equals(userLogguedHasRole("ROLE_ADMIN"))){
            Customer c = getCustomerLogged();
            if (c == null) return null;
            idBusiness = c.getIdBusiness();
            if (idBusiness == null) return null;
        }
        return idBusiness;
    }

    public Integer getRealIdCustomer(Integer idCustomer) {
        if (Boolean.FALSE.equals(userLogguedHasRole("ROLE_ADMIN"))){
            Customer c = getCustomerLogged();
            if (c == null) return null;
            idCustomer = c.getId();
            if (idCustomer == null) return null;
        }
        return idCustomer;
    }

    public Boolean havePermissionChangeResource(Integer idBusiness) {
        if (this.getLoggedUser().getRoles().stream().noneMatch(x -> x.getName().equals("ROLE_ADMIN"))
                && (!this.getCustomerLogged().getIdBusiness().equals(idBusiness))) {
            return false;
        }
        return true;
    }
}
