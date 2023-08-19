package com.jack.weChatSecurity.core.urlmatcher.urltrie;

import java.util.Collection;
import java.util.Set;

public class AuUrl {

    private String url;
    private Power power;

    public AuUrl(){
        this.power=new Power();
    }

    public void addRoles(Collection<String> roles){
        power.addRoles(roles);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<String> getRoles() {
        return power.getRoles();
    }

    public void setPower(Power power) {
        this.power = power;
    }
}
