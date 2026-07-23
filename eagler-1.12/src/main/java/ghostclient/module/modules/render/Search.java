package ghostclient.module.modules.render;

import ghostclient.module.Category;
import ghostclient.module.Module;
import ghostclient.setting.ModeValue;

/**
 * Highlights configured blocks.
 */
public class Search extends Module {

    private final ModeValue block = new ModeValue("Block", "Block to search", "Diamond", "Diamond", "Emerald", "Gold", "Iron", "Lapis");

    public Search() {
        super(Category.Render, "Search", "Highlight specific blocks.");
        addSetting(block);
    }
}
