package jinuk.project7.member;

import jakarta.persistence.*;
import jinuk.project7.sales.Sales;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String displayName;
    private String password;

    @ToString.Exclude
    @OneToMany(mappedBy = "member")
    private List<Sales> sales = new ArrayList<>();

}
