package cn.elytra.mod.forcibly_translated.mixin;

import cn.elytra.mod.forcibly_translated.ForciblyTranslated;
import dev.ftb.mods.ftbquests.client.gui.quests.ChapterPanel;
import dev.ftb.mods.ftbquests.quest.ChapterGroup;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ChapterPanel.ChapterGroupButton.class, remap = false)
public class MixinChapterGroupButton {

    @Redirect(
        method = "<init>", at = @At(
        value = "INVOKE",
        target = "Ldev/ftb/mods/ftbquests/quest/ChapterGroup;getTitle()Lnet/minecraft/network/chat/Component;"))
    private static Component forcibly_translated$redirectGetTitle(ChapterGroup instance) {
        return ForciblyTranslated.tryGetComponent(
            ForciblyTranslated.keyForChapterGroupTitle(instance),
            instance::getTitle);
    }

}
