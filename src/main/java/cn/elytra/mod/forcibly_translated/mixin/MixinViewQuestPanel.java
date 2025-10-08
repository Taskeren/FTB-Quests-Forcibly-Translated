package cn.elytra.mod.forcibly_translated.mixin;

import cn.elytra.mod.forcibly_translated.ForciblyTranslated;
import cn.elytra.mod.forcibly_translated.ForciblyTranslatedConfig;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import dev.ftb.mods.ftbquests.client.gui.quests.ViewQuestPanel;
import dev.ftb.mods.ftbquests.quest.Quest;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

/**
 * Mixin to the Quest Detail Window.
 */
@Mixin(value = ViewQuestPanel.class, remap = false)
public class MixinViewQuestPanel {

    @Shadow
    private Quest quest;

    @Redirect(
        method = "addWidgets", at = @At(
        value = "INVOKE",
        target = "Ldev/ftb/mods/ftbquests/quest/Quest;getTitle()Lnet/minecraft/network/chat/Component;"))
    private Component forcibly_translated$redirectGetTitle(Quest instance) {
        return ForciblyTranslated.tryGetComponent(ForciblyTranslated.keyForQuestTitle(instance), instance::getTitle);
    }

    @Redirect(
        method = "addWidgets", at = @At(
        value = "INVOKE",
        target = "Ldev/ftb/mods/ftbquests/quest/Quest;getSubtitle()Lnet/minecraft/network/chat/Component;"))
    private Component forcibly_translated$redirectGetSubtitle(Quest instance) {
        return ForciblyTranslated.tryGetComponent(
            ForciblyTranslated.keyForQuestSubtitle(instance),
            instance::getSubtitle);
    }

    @SuppressWarnings("unchecked")
    @Redirect(
        method = "addDescriptionText",
        at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;", ordinal = 1))
    private <E> E forcibly_translated$redirectGetDescriptionIndex(List<E> descList, int descIndex,
        @Share("nonEmptyCount") LocalIntRef nonEmptyCounter) { // <E> is Component
        if (ForciblyTranslatedConfig.generateKeysForEmptyDescription) {
            // the simplest behavior, we generate keys for all lines
            return (E) ForciblyTranslated.tryGetComponent(
                ForciblyTranslated.keyForQuestDescription(quest, descIndex),
                () -> (Component) descList.get(descIndex));
        } else {
            // the complicated behavior, we generate keys for only non-empty lines
            Component original = (Component) descList.get(descIndex);
            if (original.getString().isEmpty()) return (E) original;
            int nonEmptyIndex = nonEmptyCounter.get();
            nonEmptyCounter.set(nonEmptyCounter.get() + 1);
            return (E) ForciblyTranslated.tryGetComponent(
                ForciblyTranslated.keyForQuestDescription(
                    quest,
                    nonEmptyIndex),
                () -> (Component) descList.get(descIndex));
        }
    }

}
