package ryuzuinfiniteshop.ryuzuinfiniteshop.config;

public enum LanguageKey {
    ITEM_SEARCH_BY_VALUE("対価で検索", "Search by value"),
    ITEM_SEARCH_BY_PRODUCT("商品で検索", "Search by product"),
    ITEM_LORE_SEARCH_BY_NAME("シフトクリック: アイテムの名前で検索", "Shift click: Search by item name"),
    ITEM_SEARCH_BY_CLICK("検索するアイテムを持ってクリック", "Search by clicking on an item"),
    ITEM_LORE_SEARCH_BY_NPC("シフトクリック: NPCの名前で検索", "Shift click: Search by NPC name"),
    INVENTORY_SEARCH_TRADE("トレード サーチ", "Trade Search"),
    MESSAGE_PAGE_NAVIGATION("GUIの画面外を右クリック: 次のページに移動、左クリック: 前のページに移動できます", "Right click outside the GUI to move to the next page, left click to move to the previous page"),
    MESSAGE_UNDERSTAND_BUTTON_MESSAGE("分かった！", "got it!"),
    MESSAGE_ERROR_TRADE_DUPLICATE("重複している取引がありました", "There are duplicate trades"),
    MESSAGE_UNDERSTAND_BUTTON_TOOLTIP("これ以降メッセージを表示しない", "Don't show messages anymore"),
    ERROR_FILE_LOADING("ShopID: {0} の読み込み中にエラーが発生しました", "Error loading ShopID: {0}"),
    ERROR_FILE_CONVERTING("ShopkeepersID: {0} SISID: {1},{2},{3},{4} のShopkeepersからのコンバート中にエラーが発生しました", "An error occurred while converting ShopkeepersID: {0} SISID: {1},{2},{3},{4} from Shopkeepers"),
    COMMAND_LIST_SHOPS("ショップの一覧を表示します", "Show a list of shops"),
    COMMAND_SEARCH_TRADES("ショップや取引を検索します", "Search for shops and trades"),
    COMMAND_SPAWN_SHOP("ショップの作成または更新をします", "Create or update a shop"),
    COMMAND_OPEN_TRADE_GUI("ショップの取引画面を開きます", "Open the trade GUI of a shop"),
    COMMAND_RELOAD_ALL_DATA("全てのデータをリロードします", "Reload all data"),
    COMMAND_LOAD_ALL_DATA("全てのデータをファイルから読み取り、ショップをロードします", "Load all data from files"),
    COMMAND_UNLOAD_ALL_DATA("全てのデータをファイルに保存し、ショップをアンロードします", "Unload all data to files"),
    COMMAND_CHANGE_TRADE_LIMIT("取引回数を変更します", "Change trade limits"),
    COMMAND_ARGUMENT_REQUIRED("必須", "Required"),
    COMMAND_ARGUMENT_OPTIONAL("任意", "Optional"),
    COMMAND_PLAYER_ONLY("プレイヤーのみ実行可能です", "Only players can execute this command."),
    MESSAGE_SHOP_CREATED("ショップを設置しました", "Shop created!"),
    MESSAGE_SHOP_UPDATED("ショップを更新しました", "Shop updated!"),
    MESSAGE_ERROR_ENTITY_INVALID("有効なエンティティタイプまたはMythicMobIDを入力して下さい", "Please input a valid entity type or MythicMob ID."),
    INVENTORY_SHOP_LIST("ショップ一覧", "Shop List"),
    ITEM_TRADE_SUCCESS("購入可能", "Purchasable"),
    ITEM_NOT_ENOUGH_ITEMS("アイテムが足りません", "Not enough items"),
    ITEM_NOT_ENOUGH_SPACE("インベントリに十分な空きがありません", "Inventory is full"),
    ITEM_TRADE_LIMITED("取引上限です", "Trade is limited"),
    ITEM_TRADE_NORMAL("取引上限設定と取引のアイテム化", "Trade limit set and itemized"),
    ITEM_TRADE_INVALID("エラー発生。無効な取引です", "An error has occurred. The trade is invalid"),
    ITEM_TRADE_ONCE("クリック: 1回購入", "Click: Buy one time"),
    ITEM_TRADE_TEN("シフトクリック: 10回購入", "Shift click: Buy ten times"),
    ITEM_TRADE_STACK("ミドルクリック: 64回購入", "Middle click: Buy a stack"),
    ITEM_TRADE_REMAINING("残り取引回数: {0}", "Remaining trade limit: {0}"),
    ITEM_SEARCH_BY_VALUEORPRODUCT("対価、商品をクリック: 商品、対価で検索", "Click the item and the price to search by item and price"),
    ITEM_LOCATION("座標: {0}", "Location: {0}"),
    ITEM_EDITOR_IS_SEARCHABLE("検索可否: {0}", "Searchability: {0}"),
    ITEM_EDITOR_SEARCHABLE("可能", "Searchable"),
    ITEM_EDITOR_UNSEARCHABLE("不可 ", "Unsearchable"),
    ITEM_IS_LOCKED("ロック: {0}", "Locked: {0}"),
    ITEM_LORE_CLICK_TO_OPEN("クリック: 取引画面を開く", "Click: Open trade window"),
    ITEM_LORE_CLICK_TO_EDIT("シフトクリック: 編集画面を開く", "Shift-click: Open edit window"),
    INVENTORY_SHOP_DELETE("ショップ削除確認：", "Shop Deletion Confirmation: "),
    ITEM_EDITOR_BUTTON_CANCEL("キャンセル", "Cancel"),
    ITEM_EDITOR_BUTTON_DELETE("削除する", "Delete"),
    ITEM_EDITOR_NEW_PAGE("新規ページ", "New page"),
    INVENTORY_PAGE("ページ {0}", "Page {0}"),
    ITEM_EDITOR_SET_NAME("名前を変更する", "Set display name"),
    ITEM_CURRENT_NAME("現在の名前: {0}", "Current name: {0}"),
    ITEM_EDITOR_TELEPORT_TO_NPC("NPCにテレポートする", "Teleport to NPC"),
    ITEM_EDITOR_SET_MYTHICMOBID("MythicMobIDを設定する", "Set MythicMob ID"),
    ITEM_EDITOR_SET_ENTITYTYPE("エンティティタイプを設定する", "Set entity type"),
    ITEM_SEARCH_SELECT("検索可能", "Searchable"),
    ITEM_SEARCH_NOT_SELECTABLE("検索不可", "Not searchable"),
    ITEM_EDITOR_LOCKED("ロック", "Locked"),
    ITEM_EDITOR_UNLOCKED("アンロック", "Unlocked"),
    ITEM_EDITOR_CHANGE_DIRECTION("方向切り替え", "Switch direction"),
    ITEM_EDITOR_SHOP_DELETE("ショップを削除する", "Delete shop"),
    ITEM_EDITOR_UPDATE_SHOP("NPCをリスポーンする", "Respawn npc"),
    ITEM_EDITOR_CONVERT_TRADE_TO_ITEMS("トレード内容をアイテム化する", "Convert trades to item"),
    ITEM_EDITOR_CONVERT_SHOP_TO_ITEMS("ショップをアイテム化する", "Convert shop to item"),
    ITEM_EDITOR_LOAD_TRADES("トレードを読み込む", "Load trades"),
    ITEM_EDITOR_POWERED("帯電", "Powered"),
    ITEM_EDITOR_NOT_POWERED("通常", "Not powered"),
    ITEM_EDITOR_SIZE_INCREASE("サイズを大きくする", "Increase slime size"),
    ITEM_EDITOR_SIZE_DECREASE("サイズを小さくする", "Decrease slime size"),
    ITEM_EDITOR_BODY_COLOR("体の色を変更する", "Change body color"),
    ITEM_EDITOR_PATTERN_COLOR("模様の色を変更する", "Change pattern color"),
    ITEM_EDITOR_PATTERN("模様を変更する", "Change pattern"),
    ITEM_EDITOR_PARROT_COLOR("色の変更", "Change parrot color"),
    ITEM_EDITOR_DYE_COLOR("色の変更", "Change dye color"),
    ITEM_EDITOR_OPTIONAL_INFO("追加情報の変更", "Change optional info"),
    ITEM_EDITOR_HORSE_COLOR("色の変更", "Change horse color"),
    ITEM_EDITOR_HORSE_STYLE("模様の変更", "Change horse style"),
    ITEM_SETTINGS_JOB_CHANGE("ジョブチェンジ", "Job change"),
    ITEM_SETTINGS_BIOME_CHANGE("バイオームチェンジ", "Biome change"),
    ITEM_SETTINGS_LEVEL_CHANGE("レベルチェンジ", "Level change"),
    ITEM_SETTINGS_VISIBILITY_INVISIBLE("透明", "Invisible"),
    ITEM_SETTINGS_VISIBILITY_VISIBLE("不透明", "Visible"),
    MESSAGE_SHOP_LOCKED("現在このショップはロックされています", "This shop is currently locked"),
    MESSAGE_SHOP_EDITING("現在このショップは編集中です", "This shop is currently being edited"),
    MESSAGE_SHOP_NO_TRADES("現在このショップには取引が存在しません", "There are currently no trades available in this shop"),
    ERROR_FILE_SAVING("ShopID: {0} の保存中にエラーが発生しました", "An error occurred while saving ShopID: {0}"),
    ERROR_MYTHICMOBS_INVALID_LOADED("MythicMobsがロードされていません ID: {0}", "MythicMobs is not loaded ID: {0}"),
    ERROR_MYTHICMOBS_INVALID_ID("存在しないMMIDです ID: {0}", "Invalid MMID ID: {0}"),
    MESSAGE_ERROR_NOT_EXIST_PLAYER("そのプレイヤーは存在しません", "That player does not exist"),
    MESSAGE_ERROR_TRADE_REQUIRED_GEM("トレード圧縮宝石を持った状態で実行してください", "Please execute while holding a Trade Compression Gem"),
    COMMAND_INVALID_INTEGER("0以上の整数を入力してください", "Please input a non-negative integer"),
    MESSAGE_SUCCESS_LIMIT_CHANGE("{0}の取引上限に変更を加えました", "Changed the trade limit for {0}"),
    ITEM_SHOP_COMPRESSION_GEM("ショップ圧縮宝石:", "Shop compression gem: "),
    ITEM_SHOP_COMPRESSION_GEM_CLICK("ショップに向かって右クリック:", "Right-click on a shop: "),
    ITEM_SHOP_COMPRESSION_GEM_MEARGE("ショップの取引の取り込み", "Merge shop trades"),
    ITEM_SHOP_COMPRESSION_GEM_PLACELORE("地面に向かってシフト右クリック:", "Shift right-click on the ground: "),
    ITEM_SHOP_COMPRESSION_GEM_PLACE("ショップの設置", "Place the shop"),
    ITEM_SHOP_COMPRESSION_GEM_TYPE("ショップタイプ: ", "Shop type: "),
    ITEM_TRADE_COMPRESSION_GEM("トレード圧縮宝石", "Trade compression gem"),
    ITEM_TRADE_WITH("{0}との取引", "Trade with {0}"),
    ITEM_AGE_ADULT("大人", "Adult"),
    ITEM_AGE_CHILD("子供", "Child"),
    ITEM_EDITOR_SEARCH_ENABLED("可", "Enabled"),
    ITEM_EDITOR_SEARCH_DISABLED("不可", "Disabled"),
    ITEM_TRADE_WINDOW_OPEN("クリック: 取引画面を開く", "Click: Open trade window"),
    ITEM_EDIT_WINDOW_OPEN("シフトクリック: 編集画面を開く", "Shift click: Open edit window"),
    MESSAGE_ERROR_NOT_ENOUGH_ITEMS("アイテムが足りません", "Not enough items"),
    COMMAND_INVALID_TRADE("エラーが発生しました。無効な取引です", "An error occurred. Invalid trade"),
    MESSAGE_ERROR_TRADE_LIMITED("取引上限です", "Trade limit reached"),
    MESSAGE_ERROR_NOT_ENOUGH_SPACE("すべてを購入できませんでした", "Not enough space"),
    MESSAGE_ERROR_INVENTORY_FULL("インベントリに十分な空きがありません", "Inventory is full"),
    ITEM_SETTINGS_TRADE_SET_LIMIT("クリック: 取引上限設定", "Click: Set trade limit"),
    ITEM_SETTINGS_TRADE_LIMIT("取引上限", "Trade limit"),
    ITEM_SETTINGS_TRADE_TO_ITEM("シフトクリック: 取引のアイテム化", "Shift click: Trade to item"),
    MESSAGE_SUCCESS_SET_DISPLAY_NAME("名前が設定されました", "Display name set successfully"),
    MESSAGE_ENTER_NPC_NAME("NPCの名前をチャットに入力してください", "Enter NPC name in chat"),
    MESSAGE_ENTER_CANCEL("20秒待つか'Cancel'と入力することでキャンセルことができます", "You can cancel by waiting for 20 seconds or typing 'Cancel'"),
    MESSAGE_ENTER_NPC_NAME_COLOR("カラーコードを使う際は'&'を使用してください", "Use '&' to use color codes"),
    COMMAND_INVALID_MYTHIC_MOB_ID("有効なMythicMobIDを入力して下さい", "Please enter a valid MythicMobID"),
    MESSAGE_SUCCESS_SET_MYTHIC_MOB_ID("MythicMobIDを設定しました", "MythicMobID set successfully"),
    MESSAGE_ENTER_MYTHICMOBID("MythicMobIDを入力してください", "Enter MythicMobID"),
    MESSAGE_SHOP_NOT_FOUND("ショップが見つかりませんでした。", "Shop not found."),
    MESSAGE_NO_TRADES_AVAILABLE("ショップに取引がありません。", "No trades available in the shop."),
    MESSAGE_ERROR_NOT_MATCH("ショップタイプが違います", "Shop type mismatch"),
    MESSAGE_SUCCESS_SHOP_CREATE("{0}を作成しました", "Successfully created {0}"),
    MESSAGE_SUCCESS_SHOP_DELETE("{0}を削除しました", "Successfully deleted {0}"),
    MESSAGE_ENTER_CANCELLED("キャンセルしました", "Cancelled"),
    MESSAGE_ERROR_NOT_FOUND("ショップが見つかりませんでした", "Shop not found"),
    ITEM_SEARCH_BY_ITEM_CLICK("検索するアイテムを持ってクリック", "Click with item to search"),
    ITEM_SEARCH_BY_NPC_NAME("シフトクリック: NPCの名前で検索", "Shift click: Search by NPC name"),
    MESSAGE_SEARCH_NPC("検索するNPCの名前をチャットに入力してください", "Input the name of NPC you want to search in chat"),
    MESSAGE_CANCEL_INPUT("20秒待つか'Cancel'と入力することでキャンセルことができます", "You can cancel the operation by waiting for 20 seconds or inputting 'Cancel'"),
    MESSAGE_SEARCH_NORESULTS("検索結果がありませんでした", "No results found for search"),
    MESSAGE_ENTER_SEARCH_PROMPT("検索するアイテムの名前をチャットに入力してください", "Please enter the name of the item to search for in chat"),
    MESSAGE_FILES_RELOADING_FILES("全てのファイルをリロード中です。しばらくお待ちください。", "Reloading all files. Please wait a moment."),
    MESSAGE_FILES_LOADING_COMPLETE("全てのファイルのロードが完了しました", "All files have been loaded successfully."),
    MESSAGE_FILES_SAVING_COMPLETE("全てのファイルの保存が完了しました", "All files have been saved successfully."),
    MESSAGE_FILES_RELOADING_COMPLETE("全てのファイルのリロードが完了しました", "All files have been reloaded successfully."),
    MESSAGE_FILES_RELOADING_BLOCKED("現在リロード処理中のため、すべての処理をブロックしています。", "All processes are currently blocked due to reloading."),
    ERROR_WORLD_NOT_FOUND("ワールドが存在しません: {0}", "World not found: {0}"),
    MESSAGE_SHOP_NOT_EXIST("そのショップは存在しません", "That shop does not exist"),
    ITEM_EQUIP_HAND_MAIN("メインハンド", "Main hand"),
    ITEM_EQUIP_HELMET("ヘルメット", "Helmet"),
    ITEM_EQUIP_CHESTPLATE("チェストプレート", "Chestplate"),
    ITEM_EQUIP_LEGGINGS("レギンス", "Leggings"),
    ITEM_EQUIP_BOOTS("ブーツ", "Boots"),
    ITEM_EQUIP_HAND_OFF("オフハンド", "Off hand"),
    INVENTORY_DEFAULT_SHOP("ショップ", "Shop"),
    MESSAGE_SUCCESS_SHOP_MERGE("{0}の取引を宝石のショップにマージしました", "Successfully merged trades of {0} into the shop in gemstone");


    private final String japanese;
    private final String english;

    LanguageKey(String japanese, String english) {
        this.japanese = japanese;
        this.english = english;
    }

    public String getMessage(Object... args) {
        String message = LanguageConfig.getText(this);
        for (int i = 0; i < args.length; i++) {
            message = message.replace("{" + i + "}", String.valueOf(args[i]));
        }
        return message;
    }

    public String getLanguage(String language) {
        return language.equalsIgnoreCase("Japanese") ? japanese : english;
    }

    public String getConfigKey() {
        return name().toLowerCase().replace("_", ".");
    }
}
