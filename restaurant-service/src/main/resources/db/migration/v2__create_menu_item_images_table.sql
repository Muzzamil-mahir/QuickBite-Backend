CREATE TABLE menu_item_images (
                                  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                                  item_id UUID NOT NULL
                                      REFERENCES menu_items(id)
                                          ON DELETE CASCADE,

                                  object_key TEXT NOT NULL
                                      CONSTRAINT menu_item_image_object_key_len
                                          CHECK (length(object_key) <= 300),

                                  image_url TEXT NOT NULL
                                      CONSTRAINT menu_item_image_url_len
                                          CHECK (length(image_url) <= 500),

                                  display_order INT NOT NULL DEFAULT 0,

                                  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);