package DeliveryFriends.Backend.Service;

import DeliveryFriends.Backend.Controller.BaseException;
import DeliveryFriends.Backend.Controller.BaseResponseStatus;
import DeliveryFriends.Backend.Domain.Dto.FilenameDto;
import DeliveryFriends.Backend.Domain.Dto.Store.*;
import DeliveryFriends.Backend.Domain.Menu;
import DeliveryFriends.Backend.Domain.MenuOption;
import DeliveryFriends.Backend.Domain.MenuOptionGroup;
import DeliveryFriends.Backend.Domain.Store;
import DeliveryFriends.Backend.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final StoreMediaRepository storeMediaRepository;
    private final MenuOptionGroupRepository menuOptionGroupRepository;
    private final MenuOptionRepository menuOptionRepository;

    public Long addStore(CreateStoreDto createStoreDto) {
        Store store = new Store(createStoreDto, 0L, 0L);
        return storeRepository.save(store).getId();
    }

    public List<ReadStoresDto> getStoreList(Pageable pageable, StoreCondDto cond) {
        List<SimpleStoreDto> storeList = storeRepository.getStoreList(pageable, cond);
        List<ReadStoresDto> result = new ArrayList<>();
        for (SimpleStoreDto simpleStoreDto : storeList) {
            List<FilenameDto> medium = storeMediaRepository.getStoreMedium(simpleStoreDto.getId());
            result.add(new ReadStoresDto(simpleStoreDto, medium));
        }
        return result;
    }

    public Long addMenu(CreateMenuDto createMenuDto) {
        Optional<Store> findStore = storeRepository.findById(createMenuDto.getStoreId());
        if (!findStore.isPresent()) {
            throw new BaseException(BaseResponseStatus.CANNOT_FOUND_STORE);
        }
        Menu menu = new Menu(createMenuDto, findStore.get());
        return menuRepository.save(menu).getId();
    }

    public Long addMenuOptionGroup(CreateMenuOptionGroupDto createMenuOptionGroupDto) {
        Optional<Menu> findMenu = menuRepository.findById(createMenuOptionGroupDto.getMenuId());
        if (!findMenu.isPresent()) {
            throw new BaseException(BaseResponseStatus.CANNOT_FOUND_MENU);
        }
        MenuOptionGroup menuOptionGroup = new MenuOptionGroup(createMenuOptionGroupDto, findMenu.get());
        return menuOptionGroupRepository.save(menuOptionGroup).getId();
    }

    public Long addMenuOption(CreateMenuOptionDto createMenuOptionDto) {
        Optional<MenuOptionGroup> findMenuOptionGroup = menuOptionGroupRepository.findById(createMenuOptionDto.getMenuOptionGroupId());
        if (!findMenuOptionGroup.isPresent()) {
            throw new BaseException(BaseResponseStatus.CANNOT_FOUND_MENU_OPTION_GROUP);
        }
        MenuOption menuOptionGroup = new MenuOption(createMenuOptionDto, findMenuOptionGroup.get());
        return menuOptionRepository.save(menuOptionGroup).getId();
    }

    public List<ReadMenuDto> readMenu(Long storeId) {
        List<ReadMenuDto> result = new ArrayList<>();

        Optional<Store> findStore = storeRepository.findById(storeId);
        if (!findStore.isPresent()) {
            throw new BaseException(BaseResponseStatus.CANNOT_FOUND_STORE);
        }
        List<Menu> menus = menuRepository.findByStoreAndDeleted(findStore.get(), false);
        for (Menu menu : menus) {
            ReadMenuDto readMenuDto = new ReadMenuDto();
            readMenuDto.setId(menu.getId());
            readMenuDto.setName(menu.getName());
            readMenuDto.setExpression(menu.getExpression());
            readMenuDto.setPrice(menu.getPrice());

            List<MenuOptionGroup> menuOptionGroups = menuOptionGroupRepository.findByMenuAndDeleted(menu, false);
            List<ReadMenuOptionGroup> readMenuOptionGroups = new ArrayList<>();
            for (MenuOptionGroup menuOptionGroup : menuOptionGroups) {
                ReadMenuOptionGroup readMenuOptionGroup = new ReadMenuOptionGroup();
                readMenuOptionGroup.setId(menuOptionGroup.getId());
                readMenuOptionGroup.setMultiSelect(menuOptionGroup.getMultiSelect());
                readMenuOptionGroup.setName(menuOptionGroup.getName());

                List<ReadMenuOption> readMenuOptions = new ArrayList<>();
                List<MenuOption> menuOptions = menuOptionRepository.findByMenuOptionGroupAndDeleted(menuOptionGroup, false);
                for (MenuOption menuOption : menuOptions) {
                    ReadMenuOption readMenuOption = new ReadMenuOption();
                    readMenuOption.setId(menuOption.getId());
                    readMenuOption.setName(menuOption.getName());
                    readMenuOption.setPrice(menuOption.getPrice());
                    readMenuOption.setDefaultValue(menuOption.getDefaultValue());
                    readMenuOption.setMaxCount(menuOption.getMaxCount());
                    readMenuOptions.add(readMenuOption);
                }
                readMenuOptionGroup.setReadMenuOptionList(readMenuOptions);
            }
            readMenuDto.setReadMenuOptionGroupList(readMenuOptionGroups);
            result.add(readMenuDto);
        }
        return result;
    }
}
