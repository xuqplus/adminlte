package cn.xuqplus.adminlte.domain.query;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class A {
    @Id
    private String a;
    private String b;
    private String c;
}
