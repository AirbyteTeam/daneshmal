package com.airbyte.daneshmal.models.enums;

public enum Category {
    CommercializationServices("خدمات تجاری سازی"),
    AdvancedMachinesAndEquipment("ماشین آلات و تجهیزات پیشرفته"),
    MedicalSuppliesAndEquipment("وسایل ملزومات و تجهیزات پزشکی"),
    FoodIndustry("کشاورزی، فناوری زیستی و صنایع غذایی"),
    LaserAndPhotonics("سخت افزارهای برق و الکترونیک، لیزر و فوتونیک"),
    SoftwareIndustries("فناوری اطلاعات و ارتباطات و نرم افزارهای رایانه ای"),
    MedicineIndustries("دارو و فرآورده های پیشرفته حوزه تشخیص و درمان"),
    CulturalIndustries("صنایع فرهنگی، صنایع خلاق و علوم انسانی و اجتماعی"),
    AdvanceMaterialAndProducts("مواد پیشرفته و محصولات مبتنی بر فناوری های شیمیایی");


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
