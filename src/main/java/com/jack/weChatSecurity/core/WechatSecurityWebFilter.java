package com.jack.weChatSecurity.core;

import com.jack.weChatSecurity.context.*;
import com.jack.weChatSecurity.context.fatories.annotate.AnnotateFactory;
import com.jack.weChatSecurity.context.fatories.json.JsonContextFactory;
import com.jack.weChatSecurity.core.au.Request;
import com.jack.weChatSecurity.json.JSON;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WechatSecurityWebFilter implements Filter {
    private final String PACKAGE="package";
    private final String CONFIG_FILENAME="configFileName";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(servletRequest instanceof HttpServletRequest&&servletResponse instanceof HttpServletResponse){
            HttpServletRequest request=(HttpServletRequest) servletRequest;
            HttpServletResponse response=(HttpServletResponse) servletResponse;
            String url=request.getRequestURI();
            Request req=new Request(url, request.getParameterMap());
            ResponseMessage responseMessage =null;
            try {
                SecurityHelper.doAuthenticatorChain(req);
            }
            catch (AuthenticateException e){
                responseMessage =new ResponseMessage().setMsg(e.getMessage());
            } finally {
                //认证失败!
                if(responseMessage !=null){
                    System.out.println("error:"+responseMessage.getMsg());
                    responseMessage.setCode(1);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter writer= response.getWriter();
                    try {
                        writer.write(JSON.writeString(responseMessage));
                    } catch (Exception e) {
                        writer.write("RunError:"+e.getMessage());
                    }
                    writer.close();
                }
                else filterChain.doFilter(servletRequest, servletResponse);
            }
            //防止threadlocal溢出
            SecurityHelper.finishService();
            return;
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //初始化
        try {
            String packagePath = filterConfig.getInitParameter(PACKAGE);
            String configFilename = filterConfig.getInitParameter(CONFIG_FILENAME);
            ContextFactory contextFactory=null;
            if (packagePath!=null)
                contextFactory=new AnnotateFactory(packagePath);
            else if (configFilename!=null)
                contextFactory=new JsonContextFactory(configFilename);
            //默认加载JsonContextFactory
            if (contextFactory==null)
                contextFactory=new JsonContextFactory();
            SecurityHelper.init(contextFactory);
        }catch (ContextInitException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        try {
            SecurityHelper.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
