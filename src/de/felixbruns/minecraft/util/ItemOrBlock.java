package de.felixbruns.minecraft.util;

public enum ItemOrBlock {
	UNDEFINED(-1, "Undefined"),
	
	/* Blocks. */
	BLOCK_AIR(0, "Air"),
	BLOCK_STONE(1, "Stone"),
	BLOCK_GRASS(2, "Grass"),
	BLOCK_DIRT(3, "Dirt"),
	BLOCK_COBBLESTONE(4, "Cobblestone"),
	BLOCK_WOOD(5, "Wood"),
	BLOCK_SAPLING(6, "Sapling"),
	BLOCK_BEDROCK(7, "Bedrock"),
	BLOCK_WATER(8, "Water"),
	BLOCK_STATIONARY_WATER(9, "Stationary Water"),
	BLOCK_LAVA(10, "Lava"),
	BLOCK_STATIONARY_LAVA(11, "Stationary Lava"),
	BLOCK_SAND(12, "Sand"),
	BLOCK_GRAVEL(13, "Gravel"),
	BLOCK_GOLD_ORE(14, "Gold Ore"),
	BLOCK_IRON_ORE(15, "Iron Ore"),
	BLOCK_COAL_ORE(16, "Coal Ore"),
	BLOCK_TREE(17, "Tree"),
	BLOCK_LEAVES(18, "Leaves"),
	BLOCK_SPONGE(19, "Sponge"),
	BLOCK_GLASS(20, "Glass"),
	BLOCK_CLOTH(35, "Cloth"),
	BLOCK_YELLOW_FLOWER(37, "Yellow Flower"),
	BLOCK_RED_ROSE(38, "Red Rose"),
	BLOCK_BROWN_MUSHROOM(39, "Brown Mushroom"),
	BLOCK_RED_MUSHROOM(40, "Red Mushroom"),
	BLOCK_GOLD_BLOCK(41, "Gold Block"),
	BLOCK_IRON_BLOCK(42, "Iron Block"),
	BLOCK_DOUBLE_STEP(43, "Double Step"),
	BLOCK_STEP(44, "Step"),
	BLOCK_BRICK(45, "Brick"),
	BLOCK_TNT(46, "TNT"),
	BLOCK_BOOKSHELF(47, "Bookshelf"),
	BLOCK_MOSSY_COBBLESTONE(48, "Mossy Cobblestone"),
	BLOCK_OBSIDIAN(49, "Obsidian"),
	BLOCK_TORCH(50, "Torch"),
	BLOCK_FIRE(51, "Fire"),
	BLOCK_MOB_SPAWNER(52, "Mob Spawner"),
	BLOCK_WOOD_STAIRS(53, "Wood Stairs"),
	BLOCK_CHEST(54, "Chest"),
	BLOCK_REDSTONE_WIRE(55, "Redstone Wire"),
	BLOCK_DIAMOND_ORE(56, "Diamond Ore"),
	BLOCK_DIAMOND_BLOCK(57, "Diamond Block"),
	BLOCK_WORKBENCH(58, "Workbench"),
	BLOCK_CROPS(59, "Crops"),
	BLOCK_FIELD(60, "Soil"),
	BLOCK_FURNACE(61, "Furnace"),
	BLOCK_FURNACEACTIVE(62, "Burning Furnace"),
	BLOCK_SIGN_POST(63, "Sign Post"),
	BLOCK_WOOD_DOOR(64, "Wood Door"),
	BLOCK_LADDER(65, "Ladder"),
	BLOCK_MINECART_TRACK(66, "Minecart Track"),
	BLOCK_COBBLESTONE_STAIRS(67, "Cobblestone Stairs"),
	BLOCK_WALL_SIGN(68, "Wall Sign"),
	BLOCK_LEVER(69, "Lever"),
	BLOCK_STONE_PRESSURE_PLATE(70, "Stone Pressure Plate"),
	BLOCK_IRON_DOOR(71, "Iron Door"),
	BLOCK_WOOD_PRESSURE_PLATE(72, "Wood Pressure Plate"),
	BLOCK_REDSTONE_ORE(73, "Redstone Ore"),
	BLOCK_GLOWING_REDSTONE_ORE(74, "Glowing Redstone Ore"),
	BLOCK_REDSTONE_TORCH_OFF(75, "Redstone torch (off)"),
	BLOCK_REDSTONE_TORCH_ON(76, "Redstone torch (on)"),
	BLOCK_STONE_BUTTON(77, "Stone Button"),
	BLOCK_SNOW(78, "Snow"),
	BLOCK_ICE(79, "Ice"),
	BLOCK_SNOW_BLOCK(80, "Snow Block"),
	BLOCK_CACTUS(81, "Cactus"),
	BLOCK_CLAY(82, "Clay"),
	BLOCK_REED(83, "Reed"),
	BLOCK_JUKEBOX(84, "Jukebox"),
	BLOCK_FENCE(85, "Fence"),
	BLOCK_PUMPKIN(86, "Pumpkin"),
	BLOCK_NETHERSTONE(87, "Netherstone"),
	BLOCK_SLOW_SAND(88, "Slow Sand"),
	BLOCK_LIGHTSTONE(89, "Lightstone"),
	BLOCK_PORTAL(90, "Portal"),
	BLOCK_JACK_O_LANTERN(91, "Jack-O-Lantern"),
	
	/* Items. */
	ITEM_IRON_SPADE(256, "Iron Spade"),
	ITEM_IRON_PICKAXE(257, "Iron Pickaxe"),
	ITEM_IRON_AXE(258, "Iron Axe"),
	ITEM_FLINT_AND_STEEL(259, "Flint and Steel"),
	ITEM_APPLE(260, "Apple"),
	ITEM_BOW(261, "Bow"),
	ITEM_ARROW(262, "Arrow"),
	ITEM_COAL(263, "Coal"),
	ITEM_DIAMOND(264, "Diamond"),
	ITEM_IRON_INGOT(265, "Iron Ingot"),
	ITEM_GOLD_INGOT(266, "Gold Ingot"),
	ITEM_IRON_SWORD(267, "Iron Sword"),
	ITEM_WOOD_SWORD(268, "Wood Sword"),
	ITEM_WOOD_SPADE(269, "Wood Spade"),
	ITEM_WOOD_PICKAXE(270, "Wood Pickaxe"),
	ITEM_WOOD_AXE(271, "Wood Axe"),
	ITEM_STONE_SWORD(272, "Stone Sword"),
	ITEM_STONE_SPADE(273, "Stone Spade"),
	ITEM_STONE_PICKAXE(274, "Stone Pickaxe"),
	ITEM_STONE_AXE(275, "Stone Axe"),
	ITEM_DIAMOND_SWORD(276, "Diamond Sword"),
	ITEM_DIAMOND_SPADE(277, "Diamond Spade"),
	ITEM_DIAMOND_PICKAXE(278, "Diamond Pickaxe"),
	ITEM_DIAMOND_AXE(279, "Diamond Axe"),
	ITEM_STICK(280, "Stick"),
	ITEM_BOWL(281, "Bowl"),
	ITEM_MUSHROOM_SOUP(282, "Mushroom Soup"),
	ITEM_GOLD_SWORD(283, "Gold Sword"),
	ITEM_GOLD_SPADE(284, "Gold Spade"),
	ITEM_GOLD_PICKAXE(285, "Gold Pickaxe"),
	ITEM_GOLD_AXE(286, "Gold Axe"),
	ITEM_STRING(287, "String"),
	ITEM_FEATHER(288, "Feather"),
	ITEM_GUNPOWEDER(289, "Gunpowder"),
	ITEM_WOOD_HOE(290, "Wood Hoe"),
	ITEM_STONE_HOE(291, "Stone Hoe"),
	ITEM_IRON_HOE(292, "Iron Hoe"),
	ITEM_DIAMOND_HOE(293, "Diamond Hoe"),
	ITEM_GOLD_HOE(294, "Gold Hoe"),
	ITEM_SEEDS(295, "Seeds"),
	ITEM_WHEAT(296, "Wheat"),
	ITEM_BREAD(297, "Bread"),
	ITEM_LEATHER_HELMET(298, "Leather Helmet"),
	ITEM_LEATHER_CHESTPLATE(299, "Leather Chestplate"),
	ITEM_LEATHER_LEGS(300, "Leather Legs"),
	ITEM_LEATHER_BOOTS(301, "Leather Boots"),
	ITEM_CHAINMAIL_HELMET(302, "Chainmail Helmet"),
	ITEM_CHAINMAIL_CHESTPLATE(303, "Chainmail Chestplate"),
	ITEM_CHAINMAIL_LEGS(304, "Chainmail Legs"),
	ITEM_CHAINMAIL_BOOTS(305, "Chainmail Boots"),
	ITEM_IRON_HELMET(306, "Iron Helmet"),
	ITEM_IRON_CHESTPLATE(307, "Iron Chestplate"),
	ITEM_IRON_LEGS(308, "Iron Legs"),
	ITEM_IRON_BOOTS(309, "Iron Boots"),
	ITEM_DIAMOND_HELMET(310, "Diamond Helmet"),
	ITEM_DIAMOND_CHESTPLATE(311, "Diamond Chestplate"),
	ITEM_DIAMOND_LEGS(312, "Diamond Legs"),
	ITEM_DIAMOND_BOOTS(313, "Diamond Boots"),
	ITEM_GOLD_HELMET(314, "Gold Helmet"),
	ITEM_GOLD_CHESTPLATE(315, "Gold Chestplate"),
	ITEM_GOLD_LEGS(316, "Gold Legs"),
	ITEM_GOLD_BOOTS(317, "Gold Boots"),
	ITEM_FLINT(318, "Flint"),
	ITEM_PORK(319, "Pork"),
	ITEM_GRILLED_PORK(320, "Grilled Pork"),
	ITEM_PAINTING(321, "Paintings"),
	ITEM_GOLDEN_APPLE(322, "Golden Apple"),
	ITEM_SIGN(323, "Sign"),
	ITEM_WOOD_DOOR(324, "Wood Door"),
	ITEM_BUCKET(325, "Bucket"),
	ITEM_WATER_BUCKET(326, "Water Bucket"),
	ITEM_LAVE_BUCKET(327, "Lava Bucket"),
	ITEM_MINECART(328, "Minecart"),
	ITEM_SADDLE(329, "Saddle"),
	ITEM_IRON_DOOR(330, "Iron Door"),
	ITEM_REDSTONE_DUST(331, "Redstone Dust"),
	ITEM_SNOWBALL(332, "Snowball"),
	ITEM_BOAT(333, "Boat"),
	ITEM_LEATHER(334, "Leather"),
	ITEM_MILK_BUCKET(335, "Milk Bucket"),
	ITEM_CLAY_BRICK(336, "Clay Brick"),
	ITEM_CLAY_BALL(337, "Clay Balls"),
	ITEM_REED(338, "Reed"),
	ITEM_PAPER(339, "Paper"),
	ITEM_BOOK(340, "Book"),
	ITEM_SLIME_BALL(341, "Slime Ball"),
	ITEM_STORAGE_MINECART(342, "Storage Minecart"),
	ITEM_POWERED_MINECART(343, "Powered Minecart"),
	ITEM_EGG(344, "Egg"),
	ITEM_COMPASS(345, "Compass"),
	ITEM_FISHING_ROD(356, "Fishing Rod"),
	ITEM_WATCH(347, "Watch"),
	ITEM_LIGHTSTONE_DUST(348, "Lightstone Dust"),
	ITEM_FISH(349, "Fish"),
	ITEM_COOKED_FISH(350, "Cooked Fish"),
	ITEM_GOLD_RECORD(2256, "Gold Record"),
	ITEM_GREEN_RECORD(2257, "Green Record");
	
	private int    id;
	private String name;
	
	private ItemOrBlock(int id, String name){
		this.id   = id;
		this.name = name;
	}
	
	public short getId(){
		return (short)this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public static ItemOrBlock getById(int id){
		for(ItemOrBlock o : ItemOrBlock.values()){
			if(o.id == id){
				return o;
			}
		}
		
		return null;
	}
	
	public static ItemOrBlock getByName(String name){
		for(ItemOrBlock o : ItemOrBlock.values()){
			if(o.name.equalsIgnoreCase(name)){
				return o;
			}
		}
		
		return null;
	}
	
	public String toString(){
		return this.name;
	}
}
