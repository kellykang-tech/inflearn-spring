package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

//    @Transactional
//    public Item updateItem(Long itemId, Book param) {   //준영속 상태의 엔티티
//        Item findItem = itemRepository.findOne(itemId);
////        findItem.change(price, name, stockQuantity);  //이렇게 의미있는 메서드를 사용(역추적 해서 어디서 변경하는지 파악 가능)
//        findItem.setPrice(param.getPrice());
//        findItem.setName(param.getName());
//        findItem.setStockQuantity(param.getStockQuantity());
//        //save 안해도 됐었네...
//        return findItem;
//    }

    //더 나은 코드
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);

//        findItem.change(name, price, stockQuantity); 제일 나은 코드. 메서드 이용하는 것 (추적이 쉬움)
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
