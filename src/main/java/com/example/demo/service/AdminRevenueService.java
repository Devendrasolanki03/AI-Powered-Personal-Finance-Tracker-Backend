package com.example.demo.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.UserRepository;

@Service
@Transactional
public class AdminRevenueService {

    private final UserRepository userRepository;

    public AdminRevenueService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ========== REVENUE STATS ==========
    public Map<String, Object> getRevenueStats() {
        Map<String, Object> stats = new HashMap<>();

        long totalUsers = userRepository.count();
        
        // These are placeholder values
        // In a real app, you would have a Subscription entity
        
        // Assume 66% are paying users (free tier excluded)
        long activeSubscriptions = Math.round(totalUsers * 0.66);
        
        // Average revenue per user (₹556)
        double avgRevenuePerUser = 556.0;
        
        // Total subscription revenue
        double subscriptionRevenue = activeSubscriptions * avgRevenuePerUser;
        
        // Conversion rate (percentage of free users who convert)
        double conversionRate = 12.8;

        stats.put("subscriptionRevenue", subscriptionRevenue);
        stats.put("activeSubscriptions", activeSubscriptions);
        stats.put("conversionRate", conversionRate);
        stats.put("avgRevenuePerUser", avgRevenuePerUser);

        return stats;
    }

    // ========== REVENUE GROWTH ==========
    public List<Map<String, Object>> getRevenueGrowth(int months) {
        List<Map<String, Object>> growthData = new ArrayList<>();
        
        YearMonth currentMonth = YearMonth.now();
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                              "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        // Generate growth data for last N months
        for (int i = months - 1; i >= 0; i--) {
            YearMonth month = currentMonth.minusMonths(i);
            
            // Count users created up to this month
            LocalDate endOfMonth = month.atEndOfMonth();
            long usersUpToMonth = userRepository.countUsersCreatedBefore(endOfMonth);
            
            // Calculate revenue (simplified)
            long subscriptions = Math.round(usersUpToMonth * 0.66);
            double revenue = subscriptions * 556.0;

            Map<String, Object> data = new HashMap<>();
            data.put("month", monthNames[month.getMonthValue() - 1]);
            data.put("revenue", revenue);
            data.put("subscriptions", subscriptions);

            growthData.add(data);
        }

        return growthData;
    }

    // ========== SUBSCRIPTION PLANS BREAKDOWN ==========
    public List<Map<String, Object>> getSubscriptionPlans() {
        List<Map<String, Object>> plans = new ArrayList<>();

        long totalUsers = userRepository.count();

        // Free Plan (34%)
        Map<String, Object> freePlan = new HashMap<>();
        freePlan.put("plan", "Free");
        freePlan.put("price", 0.0);
        freePlan.put("users", Math.round(totalUsers * 0.34));
        freePlan.put("revenue", 0.0);
        freePlan.put("percentage", 34.0);
        plans.add(freePlan);

        // Basic Plan - ₹99/month (36%)
        Map<String, Object> basicPlan = new HashMap<>();
        long basicUsers = Math.round(totalUsers * 0.36);
        basicPlan.put("plan", "Basic (₹99/mo)");
        basicPlan.put("price", 99.0);
        basicPlan.put("users", basicUsers);
        basicPlan.put("revenue", basicUsers * 99.0);
        basicPlan.put("percentage", 36.0);
        plans.add(basicPlan);

        // Premium Plan - ₹199/month (22%)
        Map<String, Object> premiumPlan = new HashMap<>();
        long premiumUsers = Math.round(totalUsers * 0.22);
        premiumPlan.put("plan", "Premium (₹199/mo)");
        premiumPlan.put("price", 199.0);
        premiumPlan.put("users", premiumUsers);
        premiumPlan.put("revenue", premiumUsers * 199.0);
        premiumPlan.put("percentage", 22.0);
        plans.add(premiumPlan);

        // Enterprise Plan - ₹499/month (8%)
        Map<String, Object> enterprisePlan = new HashMap<>();
        long enterpriseUsers = Math.round(totalUsers * 0.08);
        enterprisePlan.put("plan", "Enterprise (₹499/mo)");
        enterprisePlan.put("price", 499.0);
        enterprisePlan.put("users", enterpriseUsers);
        enterprisePlan.put("revenue", enterpriseUsers * 499.0);
        enterprisePlan.put("percentage", 8.0);
        plans.add(enterprisePlan);

        return plans;
    }
}