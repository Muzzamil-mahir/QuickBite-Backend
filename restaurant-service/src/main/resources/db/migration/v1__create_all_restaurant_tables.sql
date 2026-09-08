CREATE TABLE restaurants (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    owner_user_id UUID NOT NULL,

    name TEXT NOT NULL
        CONSTRAINT restaurant_name_len CHECK (length(name) <= 255),
    CONSTRAINT name_not_blank
        CHECK (length(trim(name)) > 0),

    slug TEXT UNIQUE NOT NULL
        CONSTRAINT slug_len CHECK (length(slug) <= 255),

    description TEXT,

    phone TEXT
        CONSTRAINT phone_len CHECK (length(phone) <= 20),

    fssai_number TEXT UNIQUE NOT NULL
        CONSTRAINT fssai_number_len CHECK (length(fssai_number) <= 100),

    gstin TEXT
        CONSTRAINT gstin_len CHECK (length(gstin) <= 20),

    status TEXT NOT NULL DEFAULT 'PENDING'
        CONSTRAINT status_len CHECK (length(status) <= 50),

    rejection_reason TEXT,

    is_open BOOLEAN NOT NULL DEFAULT FALSE,

    manual_override BOOLEAN NOT NULL DEFAULT FALSE,

    commission_pct NUMERIC(5,2) NOT NULL DEFAULT 15.00,

    min_order_value NUMERIC(10,2) NOT NULL DEFAULT 0,

    avg_prep_minutes INT NOT NULL DEFAULT 20,

    avg_rating NUMERIC(3,2) NOT NULL DEFAULT 0.00,

    total_ratings INT NOT NULL DEFAULT 0,

    cuisine_tags TEXT[] NOT NULL DEFAULT '{}',

    address_line TEXT NOT NULL
        CONSTRAINT address_line_len CHECK (length(address_line) <= 500),

    city TEXT NOT NULL
        CONSTRAINT city_len CHECK (length(city) <= 100),

    pincode TEXT NOT NULL
        CONSTRAINT pincode_len CHECK (length(pincode) <= 10),

    latitude NUMERIC(10,7) NOT NULL,

    longitude NUMERIC(10,7) NOT NULL,

    bank_account_number TEXT
        CONSTRAINT bank_account_number_len CHECK (length(bank_account_number) <= 255),

    bank_ifsc TEXT
        CONSTRAINT bank_ifsc_len CHECK (length(bank_ifsc) <= 20),

    bank_account_name TEXT
        CONSTRAINT bank_account_name_len CHECK (length(bank_account_name) <= 255),

    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),

    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),

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
                                     CONSTRAINT menu_categories_name_len CHECK (length(name) <= 100),

                                 display_order INT NOT NULL DEFAULT 0,

                                 is_active BOOLEAN NOT NULL DEFAULT true,

                                 created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                                 updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE TABLE menu_items (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                            restaurant_id UUID NOT NULL
                                REFERENCES restaurants(id)
                                    ON DELETE CASCADE,

                            category_id UUID NOT NULL
                                REFERENCES menu_categories(id)
                                    ON DELETE CASCADE,

                            name TEXT NOT NULL
                                CONSTRAINT menu_items_name_len
                                    CHECK (length(name) <= 255),

                            description TEXT,

                            base_price NUMERIC(10, 2) NOT NULL
                                CONSTRAINT base_price_above_zero
                                    CHECK (base_price > 0),

                            food_type TEXT NOT NULL
                                CONSTRAINT food_type_len
                                    CHECK (length(food_type) <= 20),

                            spice_level TEXT
                                CONSTRAINT spice_level_len
                                    CHECK (length(spice_level) <= 20),

                            calories INT,

                            is_available BOOLEAN NOT NULL DEFAULT TRUE,

                            is_featured BOOLEAN NOT NULL DEFAULT FALSE,

                            display_order INT NOT NULL DEFAULT 0,

                            avg_rating NUMERIC(3, 2)
                                CONSTRAINT avg_rating_valid
                                    CHECK (avg_rating >= 0 AND avg_rating <= 5),

                            thumbnail_url TEXT
                                CONSTRAINT thumbnail_url_len
                                    CHECK (length(thumbnail_url) <= 500),

                            created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),

                            updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
