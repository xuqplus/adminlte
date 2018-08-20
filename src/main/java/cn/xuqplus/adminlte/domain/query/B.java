package cn.xuqplus.adminlte.domain.query;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class B {
    private String a;
    @Id
    private String b;
    private String c;
}
