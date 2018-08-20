package cn.xuqplus.adminlte.domain.query;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class C {
    private String a;
    private String b;
    @Id
    private String c;
}
