package moe.gensoukyo.mcgproject.common.backpack;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * @author drzzm32
 * @date 2020/3/17
 * @apiNote 幻想箱/背包的容器类
 */
public class ContainerGensoChest extends Container {

    private final IInventory lowerChestInventory;
    private final int numRows;

    public ContainerGensoChest(IInventory playerPack, IInventory backpack, EntityPlayer player) {
        this.lowerChestInventory = backpack;
        this.numRows = backpack.getSizeInventory() / BackpackCore.COLUMN;
        backpack.openInventory(player);
        int offset = (this.numRows - 4) * 18;

        int i, j; final int OFFSET_X = 170;
        // 箱子
        for(i = 0; i < this.numRows; ++i) {
            for(j = 0; j < BackpackCore.COLUMN; ++j) {
                this.addSlotToContainer(new Slot(backpack, j + i * BackpackCore.COLUMN, 8 + j * 18, 18 + i * 18));
            }
        }

        // 玩家背包
        for(i = 0; i < 3; ++i) {
            for(j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerPack, j + i * 9 + 9, OFFSET_X + j * 18, 103 + i * 18 + offset));
            }
        }

        // 玩家物品栏
        for(i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(playerPack, i, OFFSET_X + i * 18, 161 + offset));
        }

    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player) {
        return this.lowerChestInventory.isUsableByPlayer(player);
    }

    @Override
    @Nonnull
    public ItemStack transferStackInSlot(EntityPlayer player, int i) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            result = stack.copy();
            if (i < this.numRows * BackpackCore.COLUMN) {
                if (!this.mergeItemStack(stack, this.numRows * BackpackCore.COLUMN, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(stack, 0, this.numRows * BackpackCore.COLUMN, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return result;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        this.lowerChestInventory.closeInventory(player);
    }

}
