package me.fakepumpkin7.pumpkinframework.gui.menu;

import lombok.Getter;
import lombok.Setter;
import me.fakepumpkin7.pumpkinframework.gui.menu.buttons.NextButton;
import me.fakepumpkin7.pumpkinframework.gui.menu.buttons.PrevButton;
import me.fakepumpkin7.pumpkinframework.items.ItemUtil;
import org.bukkit.inventory.ItemStack;

public class PaginatedMenu extends Menu {

    private int maxItemsPerPage = super.getSize() - 9;

    private PaginatedMenu current = this, next, previous;

    @Getter
    private int currentPageNumber = 1, maxPages = 1;
    public PaginatedMenu(String name, int rows) {
        super(name, rows+1);
        setNextPrevButtons();
    }


    public void setInfo(Button button){
        setButton(getSize() - 5, button);
    }
    public void setInfo(ItemStack item){
        setItem(getSize() - 5, item);
    }

    private void setNextPrevButtons(){
        Button nextButton = new NextButton(this);
        Button prevButton = new PrevButton(this);
        setButton(getSize() - 4, nextButton);
        setButton(getSize() - 6, prevButton);
    }

    public void appendButton(Button button) {

        for (int i = 0; i < maxItemsPerPage; i++) {

            if(ItemUtil.isValid(current.inventory.getItem(i))) continue;

            current.setButton(i, button);
            return;
        }

        appendPage();
        current.appendButton(button);
    }

    public PaginatedMenu appendPage() {
        PaginatedMenu newPage = new PaginatedMenu(super.getName(), super.getRows() - 1);

        newPage.previous = current;
        current.next = newPage;
        current = newPage;

        setMaxPages(getMaxPages() + 1);


        return newPage;
    }

    public PaginatedMenu getNext() {
        return next;
    }

    public PaginatedMenu getPrevious() {
        return previous;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = Math.min(currentPageNumber, maxPages);
    }

    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }
}
