package cn.elytra.mod.forcibly_translated.mixin;

import cn.elytra.mod.forcibly_translated.ForciblyTranslated;
import dev.ftb.mods.ftbquests.client.gui.quests.ChapterPanel;
import dev.ftb.mods.ftbquests.quest.Chapter;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ChapterPanel.ChapterButton.class, remap = false)
public class MixinChapterButton {

    @Redirect(
        method = "<init>", at = @At(
        value = "INVOKE",
        target = "Ldev/ftb/mods/ftbquests/quest/Chapter;getTitle()Lnet/minecraft/network/chat/Component;"))
    private static Component forcibly_translated$redirectGetTitle(Chapter instance) {
        return ForciblyTranslated.tryGetComponent(ForciblyTranslated.keyForChapterTitle(instance), instance::getTitle);
    }

}
