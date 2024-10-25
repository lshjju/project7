package jinuk.project7.sales;

import jinuk.project7.member.CustomUser;
import jinuk.project7.member.Member;
import jinuk.project7.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SalesController {

    private final SalesRepository salesRepository;
    private final MemberRepository memberRepository;

    @PostMapping("/order")
    String postOrder(@RequestParam String title, @RequestParam Integer price, @RequestParam Integer count, Authentication auth){
        Sales sales = new Sales();
        sales.setCount(count);
        sales.setPrice(price);
        sales.setItemName(title);
        CustomUser user = (CustomUser) auth.getPrincipal();
        var member = new Member();
        member.setId(user.id);
        sales.setMember(member);
//        System.out.println(user.id);
//        sales.setMemberId(user.id);
        salesRepository.save(sales);
        return "list.html";
    }

    @GetMapping("/order/all")
    String getOrderAll(){
        var result = memberRepository.findById(1L);
        System.out.println(result.get().getSales());
/*        List<Sales> result = salesRepository.customFindAll();
        System.out.println(result.get(0));*/
/*        var salesDto = new SalesDto();
        salesDto.itemName = result.get(0).getItemName();
        salesDto.price = result.get(0).getPrice();
        salesDto.username = result.get(0).getMember().getUsername();*/
//        System.out.println(result.get(0).getMemberId());
        return "sales.html";
    }

}

/*class SalesDto{
    public String itemName;
    public Integer price;
    public String username;
}*/
