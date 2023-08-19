package com.jack.weChatSecurity.core;

/**
 * 会话主体
 */
public interface WeChatUser {

    /**
     * 获取登录token
     * @return
     */
    String getToken();

    /**
     * 获取微信ID
     * @return
     */
    String getId();

    /**
     * 获取用户第一次登录时间
     * @return
     */
    long getRegisterTime();

    /**
     * 最后一次查看或修改的时间
     * @return
     */
    long getLastModifyTime();

    /**
     * 更改lastModifyTime
     */
    void modify();

    /**
     * 是否记住该用户
     */
    void rememberMe(long time);

    <T> void setAttribute(String name,T value);

    <T> T getAttribute(String name);

    /**
     * 判断用户是否拥有某角色权限
     * @param role
     * @return
     */
    boolean hasRole(String role);

    boolean hasRoles(String... roles);

}
