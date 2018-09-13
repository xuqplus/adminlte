package cn.xuqplus.adminlte.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping("json")
public class JsonSerializerController {

    @Autowired
    ApplicationContext context;

    @GetMapping("a")
    public A a() {
        return new A();
    }

    @GetMapping("b")
    public A b() {
        return new A(true);
    }

    @Data
    public static class A {
        private int _int;
        private Integer __Integer;
        private byte _byte;
        private Byte __Byte;
        private short _short;
        private Short __Short;
        private long _long;
        private Long __Long;
        private float _float;
        private Float __Float;
        private double _double;
        private Double __Double;
        private boolean _boolean;
        private Boolean __Boolean;
        private char _char;
        private Character __Character;
        private String _String;
        private List _List;
        private ArrayList _ArrayList;
        private Set _Set;
        private HashSet _HashSet;
        private Map _Map;
        private HashMap _HashMap;
        private BigInteger _BigInteger;
        private BigDecimal _BigDecimal;
        private Object _Object;

        public A() {
        }

        public A(boolean b) {
            this._int = 1;
            this.__Integer = 1;
            this._byte = 1;
            this.__Byte = 1;
            this._short = 1;
            this.__Short = 1;
            this._long = 1L;
            this.__Long = 1L;
            this._float = 1F;
            this.__Float = 1F;
            this._double = 1D;
            this.__Double = 1D;
            this._boolean = true;
            this.__Boolean = true;
            this._char = 1;
            this.__Character = Character.MAX_VALUE;
            this._String = "1";
            this._List = new ArrayList();
            this._ArrayList = new ArrayList();
            this._Set = new HashSet();
            this._HashSet = new HashSet();
            this._Map = new HashMap();
            this._HashMap = new HashMap();
            this._BigInteger = BigInteger.ZERO;
            this._BigDecimal = BigDecimal.ZERO;
            this._Object = new Object() {
                public String a = "aaa";
            };
        }
    }
}
