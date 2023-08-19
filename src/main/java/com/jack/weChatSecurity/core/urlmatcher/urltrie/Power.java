package com.jack.weChatSecurity.core.urlmatcher.urltrie;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Power {

    private Set<String> roles;

    public Power(){}

    public Set<String> getRoles() {
        return roles;
    }

    public void addRoles(Collection<String> roles){
        if(this.roles==null)
            this.roles=new HashSet<>();
        this.roles.addAll(roles);
    }

    public boolean roleEmpty(){
        if(this.roles==null||this.roles.size()==0)
            return true;
        return false;
    }

    @Override
    public String toString() {
        return "Power{" +
                "roles=" + roles +
                '}';
    }
}
