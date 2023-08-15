package com.airbyte.daneshmal.models.enums;

public enum Category {
    commercializationservices("خدمات تجاری سازی"),
    advancedmachinesandequipment("ماشین آلات و تجهیزات پیشرفته"),
    medicalsuppliesandequipment("وسایل ملزومات و تجهیزات پزشکی"),
    foodindustry("کشاورزی، فناوری زیستی و صنایع غذایی"),
    laserandphotonics("سخت افزارهای برق و الکترونیک، لیزر و فوتونیک"),
    softwareindustries("فناوری اطلاعات و ارتباطات و نرم افزارهای رایانه ای"),
    medicineindustries("دارو و فرآورده های پیشرفته حوزه تشخیص و درمان"),
    culturalindustries("صنایع فرهنگی، صنایع خلاق و علوم انسانی و اجتماعی"),
    advancematerialandproducts("مواد پیشرفته و محصولات مبتنی بر فناوری های شیمیایی");


    private final String persianName;

    Category(String persianName) {
        this.persianName = persianName;
    }

    public String getPersianName() {
        return persianName;
    }

    public Category getByPersianName(String persianName) {
        for (Category category : Category.values()) {
            if (category.getPersianName().equals(persianName)) {
                return category;
            }
        }
        return null;
    }
}
