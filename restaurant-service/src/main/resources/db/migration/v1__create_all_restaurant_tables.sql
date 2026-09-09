CREATE TABLE restaurants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    owner_user_id UUID NOT NULL,

    name TEXT NOT NULL
        CONSTRAINT restaurant_name_len
            CHECK (length(name) <= 255),

    CONSTRAINT restaurant_name_not_blank
        CHECK (length(trim(name)) > 0),

    slug TEXT UNIQUE NOT NULL
        CONSTRAINT slug_len
            CHECK (length(slug) <= 255),

    CONSTRAINT slug_not_blank
        CHECK (length(trim(slug)) > 0),

    description TEXT,

    phone TEXT
        CONSTRAINT phone_len
            CHECK (length(phone) <= 20),

    fssai_number TEXT UNIQUE NOT NULL
        CONSTRAINT fssai_number_len
            CHECK (length(fssai_number) <= 100),

    gstin TEXT
        CONSTRAINT gstin_len
            CHECK (length(gstin) <= 20),

    status TEXT NOT NULL DEFAULT 'PENDING'
        CONSTRAINT status_valid
            CHECK (
                status IN (
                    'PENDING',
                    'ACTIVE',
                    'SUSPENDED',
                    'REJECTED'
                )
            ),

    rejection_reason TEXT,

    is_open BOOLEAN NOT NULL DEFAULT FALSE,

    manual_override BOOLEAN NOT NULL DEFAULT FALSE,

    commission_pct NUMERIC(5,2)
        NOT NULL
        DEFAULT 15.00
        CONSTRAINT commission_pct_valid
            CHECK (commission_pct >= 0 AND commission_pct <= 100),

    min_order_value NUMERIC(10,2)
        NOT NULL
        DEFAULT 0
        CONSTRAINT min_order_value_valid
            CHECK (min_order_value >= 0),

    avg_prep_minutes INT
        NOT NULL
        DEFAULT 20
        CONSTRAINT avg_prep_minutes_valid
            CHECK (avg_prep_minutes > 0),

    avg_rating NUMERIC(3,2)
        NOT NULL
        DEFAULT 0.00
        CONSTRAINT restaurant_avg_rating_valid
            CHECK (avg_rating >= 0 AND avg_rating <= 5),

    total_ratings INT
        NOT NULL
        DEFAULT 0
        CONSTRAINT total_ratings_valid
            CHECK (total_ratings >= 0),

    cuisine_tags TEXT[] NOT NULL DEFAULT '{}',

    address_line TEXT NOT NULL
        CONSTRAINT address_line_len
            CHECK (length(address_line) <= 500),

    city TEXT NOT NULL
        CONSTRAINT city_len
            CHECK (length(city) <= 100),

    pincode TEXT NOT NULL
        CONSTRAINT pincode_len
            CHECK (length(pincode) <= 10),

    latitude NUMERIC(10,7)
        NOT NULL
        CONSTRAINT latitude_valid
            CHECK (latitude BETWEEN -90 AND 90),

    longitude NUMERIC(10,7)
        NOT NULL
        CONSTRAINT longitude_valid
            CHECK (longitude BETWEEN -180 AND 180),

    bank_account_number TEXT
        CONSTRAINT bank_account_number_len
            CHECK (length(bank_account_number) <= 255),

    bank_ifsc TEXT
        CONSTRAINT bank_ifsc_len
            CHECK (length(bank_ifsc) <= 20),

    bank_account_name TEXT
        CONSTRAINT bank_account_name_len
            CHECK (length(bank_account_name) <= 255),

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    created_by UUID,

    updated_by UUID
);

CREATE TABLE operating_hours (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    restaurant_id UUID NOT NULL
        REFERENCES restaurants(id)
        ON DELETE CASCADE,

    day_of_week INT NOT NULL
        CHECK (day_of_week BETWEEN 0 AND 6),

    open_time TIME NOT NULL,

    close_time TIME NOT NULL,

    is_closed BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT unique_restaurant_day
        UNIQUE (restaurant_id, day_of_week)
);

CREATE TABLE menu_categories (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    restaurant_id UUID NOT NULL
        REFERENCES restaurants(id)
        ON DELETE CASCADE,

    name TEXT NOT NULL
        CONSTRAINT menu_categories_name_len
            CHECK (length(name) <= 100),

    CONSTRAINT menu_categories_name_not_blank
        CHECK (length(trim(name)) > 0),

    display_order INT NOT NULL DEFAULT 0,

    is_active BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    CONSTRAINT unique_restaurant_category_name
        UNIQUE (restaurant_id, name)
);

CREATE TABLE menu_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    restaurant_id UUID NOT NULL
        REFERENCES restaurants(id)
        ON DELETE CASCADE,

    category_id UUID NOT NULL
        REFERENCES menu_categories(id)
        ON DELETE RESTRICT,

    name TEXT NOT NULL
        CONSTRAINT menu_items_name_len
            CHECK (length(name) <= 255),

    CONSTRAINT menu_items_name_not_blank
        CHECK (length(trim(name)) > 0),

    description TEXT,

    base_price NUMERIC(10,2) NOT NULL
        CONSTRAINT base_price_above_zero
            CHECK (base_price > 0),

    food_type TEXT NOT NULL
        CONSTRAINT food_type_valid
            CHECK (
                food_type IN (
                    'VEG',
                    'NON_VEG',
                    'VEGAN',
                    'EGG'
                )
            ),

    spice_level TEXT
        CONSTRAINT spice_level_valid
            CHECK (
                spice_level IS NULL
                OR spice_level IN (
                    'MILD',
                    'MEDIUM',
                    'HOT',
                    'EXTRA_HOT'
                )
            ),

    calories INT,

    is_available BOOLEAN NOT NULL DEFAULT TRUE,

    is_featured BOOLEAN NOT NULL DEFAULT FALSE,

    display_order INT NOT NULL DEFAULT 0,

    avg_rating NUMERIC(3,2)
        CONSTRAINT menu_item_avg_rating_valid
            CHECK (avg_rating >= 0 AND avg_rating <= 5),

    thumbnail_url TEXT
        CONSTRAINT thumbnail_url_len
            CHECK (length(thumbnail_url) <= 500),

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE item_customisation_groups (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    item_id UUID NOT NULL
        REFERENCES menu_items(id)
        ON DELETE CASCADE,

    name TEXT NOT NULL
        CONSTRAINT item_customisation_groups_name_len
            CHECK (length(name) <= 100),

    CONSTRAINT item_customisation_groups_name_not_blank
        CHECK (length(trim(name)) > 0),

    is_required BOOLEAN NOT NULL DEFAULT FALSE,

    min_selections INT NOT NULL DEFAULT 0,

    max_selections INT NOT NULL DEFAULT 1,

    display_order INT NOT NULL DEFAULT 0,

    CONSTRAINT valid_selection_range
        CHECK (max_selections >= min_selections)
);

CREATE TABLE item_customisation_options (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    group_id UUID NOT NULL
        REFERENCES item_customisation_groups(id)
        ON DELETE CASCADE,

    name TEXT NOT NULL
        CONSTRAINT item_customisation_options_name_len
            CHECK (length(name) <= 100),

    CONSTRAINT item_customisation_options_name_not_blank
        CHECK (length(trim(name)) > 0),

    additional_price NUMERIC(10,2) NOT NULL DEFAULT 0,

    is_available BOOLEAN NOT NULL DEFAULT TRUE,

    display_order INT NOT NULL DEFAULT 0
);

CREATE TABLE restaurant_images (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    restaurant_id UUID NOT NULL
        REFERENCES restaurants(id)
        ON DELETE CASCADE,

    image_url TEXT NOT NULL
        CONSTRAINT image_url_len
            CHECK (length(image_url) <= 500),

    object_key TEXT NOT NULL
        CONSTRAINT object_key_len
            CHECK (length(object_key) <= 300),

    image_type TEXT NOT NULL
        CONSTRAINT image_type_valid
            CHECK (
                image_type IN (
                    'BANNER',
                    'THUMBNAIL',
                    'GALLERY'
                )
            ),

    display_order INT NOT NULL DEFAULT 0,

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE restaurant_reviews (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    restaurant_id UUID NOT NULL
        REFERENCES restaurants(id),

    order_id UUID NOT NULL UNIQUE,

    customer_id UUID NOT NULL,

    rating INT NOT NULL
        CHECK (rating BETWEEN 1 AND 5),

    review_text TEXT,

    owner_reply TEXT,

    is_flagged BOOLEAN NOT NULL DEFAULT FALSE,

    flag_reason TEXT
        CONSTRAINT flag_reason_len
            CHECK (length(flag_reason) <= 255),

    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

    replied_at TIMESTAMPTZ
);
