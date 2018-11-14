package com.example.a123.recepts_rebuild_var_2.database;

public class MenuDbSchemka {
    public static final class MenuTable{
        public static final String NAME = "menus";
        public static final String NAMESTEPS = "menu_by_steps";

        public static final class ColsTableReceipts{
            public static final String id = "uuid";
            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String INGREDIENTS = "ingredients";
            public static final String IMAGE_PREVIEW = "image_preview";
            public static final String IMAGE_DESCRIPTION = "image_description";
        }

        public static final class ColsTableReceiptsByStep{
            public static final String id = "uuid";
            public static final String DESCRIPTION = "description";
            public static final String STEPS = "STEP";
            public static final String STEPS_IMAGE = "IMAGE_STEP";

        }

    }
}
