package jinuk.project7.item;

import jinuk.project7.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final S3Service s3Service;
    private final CommentRepository commentRepository;

    @GetMapping("/list")
    String list(Model model) {
        List<Item> result = itemRepository.findAll();
//        System.out.println(result.get(1).title);
        model.addAttribute("items", result);
        return "list.html";
    }

    @GetMapping("/write")
    String write() {
        return "write.html";
    }

    @PostMapping("/add")
    String addPost(String title, Integer price) {
        itemService.saveItem(title, price);
/*        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);*/
/*        System.out.println(title);
        System.out.println(price);*/
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model) {

        commentRepository.findAllByParentId(1L);

        Optional<Item> result = itemRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("data", result.get());
            return "detail.html";
        } else {
            return "redirect:/list";

        }
//        System.out.println(result.get());
    }

    @GetMapping("/edit/{id}")
    String edit(@PathVariable Long id, Model model) {
        Optional<Item> result = itemRepository.findById(id);
        if (result.isPresent()) {
            model.addAttribute("data", result.get());
            return "edit.html";
        } else {
            return "redirect:/list";

        }
//        System.out.println(result);
    }

    @PostMapping("/edit")
    String editItem(Long id, String title, Integer price) {
        Item item = new Item();
        item.setId(id);
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);
        return "redirect:/list";
    }

    @DeleteMapping("/item")
    ResponseEntity<String> deleteItem(Long id) {
        itemRepository.deleteById(id);
        return ResponseEntity.status(200).body("삭제완료");
    }

    @GetMapping("/list/page/{abc}")
    String getListPage(Model model, @PathVariable Integer abc) {
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(abc - 1, 5));
        model.addAttribute("items", result);
        return "list.html";
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    String getURL(@RequestParam String filename) {
        var result = s3Service.createPresignedUrl("test/" + filename);
        System.out.println(result);
        return result;
    }

    @PostMapping("/search")
    String postSearch(@RequestParam String searchText){
        var result = itemRepository.rawQuery1(searchText);
//        var result = itemRepository.findAllByTitleContains(searchText);
        System.out.println(result);
        return "list.html";
    }











}
