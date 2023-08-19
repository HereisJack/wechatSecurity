package com.jack.weChatSecurity.json;

public class JsonString implements JsonValue {
    private String values;
    private int type;

    private static final int STRING=0;
    private static final int NUMBER=1;
    private static final int BOOLEAN=2;
    private static final int NULL=3;

    public JsonString(String s)throws JsonException{
        this.values=s;
        if(checkNull())
            this.type=NULL;
        else if(checkBoolean())
            this.type=BOOLEAN;
        else if(checkNumber())
            this.type=NUMBER;
        else if (checkString()){
            this.values=s.substring(1,s.length()-1);
            this.type=STRING;
        }else throw new JsonException("数据格式有误");
    }

    @Override
    public <T> T toValue(Class<T> type) throws Exception {
        T t=null;
        if(type==short.class){
            t= (T) toShort();
        }
        if(type==int.class){
            t= (T) toInt();
        }
        if(type==double.class){
            t= (T) toDouble();
        }
        if(type==long.class){
            t= (T) toLong();
        }
        if(type==float.class){
            t= (T) toFloat();
        }
        if(type==char.class){
            t= (T) toChar();
        }
        if(type==byte.class){
            t= (T) toByte();
        }
        if(type==boolean.class){
            t= (T) toBoolean();
        }
        if(type==String.class){
            t= (T) values;
        }
        if(type==null){
            t= null;
        }
        return t;
    }

    public Short toShort()throws Exception{
        if (type==NULL)
            return Short.parseShort("0");
        return Short.parseShort(values);
    }

    public Integer toInt()throws Exception{
        if (type==NULL)
            return Integer.parseInt("0");
        return Integer.parseInt(values);
    }

    public Double toDouble()throws Exception{
        if (type==NULL)
            return Double.parseDouble("0.0");
        return Double.parseDouble(values);
    }

    public Long toLong()throws Exception{
        if (type==NULL)
            return Long.parseLong("0");
        return Long.parseLong(values);
    }

    public Float toFloat()throws Exception{
        if (type==NULL)
            return Float.parseFloat("0.0");
        return Float.parseFloat(values);
    }

    public Character toChar()throws Exception{
        if (type==NULL)
            return '\0';
        if(values.length()>1)
            throw new JsonException("非字符型数据");
        return values.charAt(0);
    }

    public Byte toByte()throws Exception{
        if (type==NULL)
            return Byte.parseByte("0");
        return Byte.parseByte(new String(values));
    }

    public Boolean toBoolean()throws Exception{
        if ("true".equals(values)){
            return true;
        }
       return false;
    }

    public boolean checkBoolean(){
        if ((values.length()==4&&"true".equals(values))||(values.length()==5&&"false".equals(values)))
            return true;
        return false;
    }

    private boolean checkNull(){
        if (values.length()==4&&"null".equals(values))
            return true;
        return false;
    }

    private boolean checkNumber(){
        if (values.charAt(0)>='0'&&values.charAt(0)<='9')
            return true;
        return false;
    }

    private boolean checkString(){
        if (values.startsWith("\"")&&values.endsWith("\""))
            return true;
        return false;
    }

    @Override
    public String toJsonString() throws Exception {
        if (type==NUMBER||type==BOOLEAN||type==NULL)
            return values+",";
        //转义字符待处理
        return "\""+values+"\",";
    }

}
