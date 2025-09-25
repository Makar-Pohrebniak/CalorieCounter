package service;

import model.User;

public class CalorieCalculator {
    public static double[] calculateNutrition(User user) {
        double calories = calculateCalories(user);

        double proteinMin = user.getWeight() * 1.5;
        double proteinMax = user.getWeight() * 2.2;

        double fatMin = user.getWeight() * 0.8;
        double fatMax = user.getWeight() * 1.2;

        double proteinAvg = (proteinMin + proteinMax) / 2.0;
        double fatAvg = (fatMin + fatMax) / 2.0;

        double carbs = (calories - proteinAvg * 4 - fatAvg * 9) / 4;

        return new double[]{calories, proteinMin, proteinMax, fatMin, fatMax, carbs};
    }

    public static double calculateCalories(User user) {
        double bmr;
        if ("male".equalsIgnoreCase(user.getGender())) {
            bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() + 5;
        } else {
            bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge() - 161;
        }
        return bmr * user.getActivity();
    }
    }

