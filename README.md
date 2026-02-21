# 📊 AI-Powered Personal Finance Tracker

## 📌 Project Overview
The **AI-Powered Personal Finance Tracker** is a full-stack web application designed to help users manage their personal finances efficiently.  
It allows users to track income and expenses, analyze spending patterns, plan budgets, and receive **AI-powered financial insights and recommendations using Google Gemini AI**.

This project follows **industry-standard layered architecture** with **secure JWT authentication**, **Google OAuth integration**, and **AI integration using Spring AI with Google Gemini**.

---

## 🎯 Main Goal of the Project
The main goal of this project is to help users:

- ✅ Track income and expenses with detailed categorization
- ✅ Analyze spending behavior with interactive charts
- ✅ Set and monitor category-wise budgets with real-time alerts
- ✅ Get AI-based financial advice and personalized recommendations using **Google Gemini**
- ✅ Receive intelligent budget warnings and overspending notifications
- ✅ View comprehensive financial analytics and reports
- ✅ Improve saving habits through actionable AI-driven insights

Instead of just showing raw financial data, the system converts data into **actionable financial intelligence** with real-time monitoring and proactive alerts.

---

## 🧠 Why This Project is Important
Many people track expenses manually but fail to understand spending patterns and lack real-time awareness of budget violations.  

This project bridges that gap by:

- ✅ Providing automated financial analysis with interactive dashboards
- ✅ Generating AI-driven savings recommendations using **Google Gemini**
- ✅ **Real-time budget alerts** when spending approaches or exceeds limits
- ✅ Category-wise spending breakdown with visual analytics
- ✅ Helping users build financial discipline and smart financial habits
- ✅ Location-aware financial advice (India-specific recommendations)

---

## 🏗️ System Architecture

```
┌─────────────────┐
│  React Frontend │
│   (Vite + React)│
└────────┬────────┘
         │
    REST API (JSON)
         │
┌────────▼─────────────┐
│ Spring Boot Backend  │
│  (Java 17 + Maven)   │
└────────┬─────────────┘
         │
    ┌────▼────────────────┐
    │  Spring AI + Gemini │
    │  Google Gemini API  │
    └─────────────────────┘
         │
┌────────▼────────┐
│ MySQL Database  │
└─────────────────┘
```

---

## 🛠️ Technologies Used

### 🔹 Backend
- **Java 17** - Programming language
- **Spring Boot 3.3.5** - Framework
- **Spring Security** - JWT + OAuth2 Authentication
- **Spring Data JPA & Hibernate** - ORM
- **Spring AI** - AI integration framework
- **Google Gemini AI** - AI-powered financial advisor
- **Maven** - Dependency management
- **MySQL 8** - Relational database

### 🔹 Database
- **MySQL 8.0+**
- **JPA/Hibernate** for ORM
- **Category preloading** with DataLoader
- **Optimized queries** with JOIN FETCH

### 🔹 Frontend
- **React 18.x** - UI library
- **Vite** - Build tool (faster than Create React App)
- **React Router 6** - Client-side routing
- **Axios** - HTTP client
- **Recharts** - Data visualization & charts
- **Framer Motion** - Smooth animations
- **React Hot Toast** - Toast notifications
- **React Markdown** - Markdown rendering for AI responses
- **Tailwind CSS** - Utility-first styling
- **Lucide React** - Modern icon library

### 🔹 AI Integration
- **Google Gemini Pro** - AI model
- **Spring AI Framework** - Gemini integration
- **ChatClient** - Unified AI interface
- **Context-aware prompting** - Location and spending data
- **Error handling** - Quota management and fallbacks

### 🔹 Authentication & Security
- **JWT (JSON Web Tokens)** - Stateless authentication
- **Google OAuth 2.0** - Social login
- **BCrypt** - Password hashing
- **Role-based access control** - USER and ADMIN roles
- **JWT Filter** - Request validation
- **CORS** - Cross-origin configuration

### 🔹 Tools & Utilities
- **Postman** - API testing
- **Git & GitHub** - Version control
- **IntelliJ IDEA / VS Code** - IDEs
- **MySQL Workbench** - Database management
- **Chrome DevTools** - Frontend debugging

---

## 🚀 Features

### 👤 User Features
1. **Authentication**
   - ✅ Local registration with email/password
   - ✅ Local login with JWT
   - ✅ Google OAuth 2.0 login
   - ✅ Secure password hashing with BCrypt
   - ✅ Token-based session management

2. **Expense Management**
   - ✅ Add, edit, delete expenses
   - ✅ Category-wise expense tracking (12 predefined categories)
   - ✅ Date-based filtering
   - ✅ Pagination support
   - ✅ Monthly and yearly summaries

3. **Income Management**
   - ✅ Add, edit, delete income sources
   - ✅ Multiple income sources tracking
   - ✅ Monthly income reports
   - ✅ Income vs Expense comparison

4. **Budget Management**
   - ✅ Set monthly budget limits per category
   - ✅ Real-time budget tracking
   - ✅ **Automated budget alerts** (WARNING, CRITICAL, EXCEEDED)
   - ✅ Budget vs actual spending visualization
   - ✅ Budget status indicators with color coding
   - ✅ Overspending notifications with exact amounts
   - ✅ Budget progress bars with animations

5. **Dashboard & Analytics**
   - ✅ Total income, expenses, and savings overview
   - ✅ Monthly expense trends (line charts)
   - ✅ Category-wise spending breakdown (pie charts)
   - ✅ Savings rate calculation
   - ✅ Recent transactions list
   - ✅ Budget alert notifications in navbar

6. **AI Financial Advisor (Gemini Powered)**
   - ✅ **Personalized financial advice** based on spending patterns
   - ✅ **Custom chat interface** - Ask any finance question
   - ✅ **Markdown-formatted responses** for better readability
   - ✅ **Location-aware recommendations** (India-specific advice)
   - ✅ **Savings tips** and cost-cutting strategies
   - ✅ **Budget optimization** suggestions
   - ✅ **Spending behavior analysis**
   - ✅ **History tracking** - Save and revisit past insights
   - ✅ **Manual generation** - No auto-calls, user-controlled
   - ✅ **Graceful error handling** with fallback advice

7. **Profile Management**
   - ✅ View and update profile information
   - ✅ Change password
   - ✅ Location settings (City, State, Country)
   - ✅ Account statistics

8. **Notifications & Alerts**
   - ✅ Real-time budget alerts in navbar (badge with count)
   - ✅ Toast notifications for important events
   - ✅ Budget status indicators (SAFE, WARNING, CRITICAL, EXCEEDED)
   - ✅ Visual progress bars with color coding

### 👨‍💼 Admin Features
1. **Admin Dashboard**
   - ✅ System-wide statistics (total users, expenses, AI insights)
   - ✅ Monthly expense charts (all users)
   - ✅ Category distribution analytics
   - ✅ Growth metrics and KPIs

2. **User Management**
   - ✅ View all registered users
   - ✅ Search and filter users
   - ✅ Block/unblock user accounts
   - ✅ View user expense summaries
   - ✅ Pagination support

3. **Expense Analytics**
   - ✅ Category-wise expense analysis
   - ✅ Spending trends over time
   - ✅ Distribution charts
   - ✅ KPI monitoring

4. **AI Insights Monitoring**
   - ✅ View all generated AI insights
   - ✅ Filter by type (ANALYSIS, SAVINGS, etc.)
   - ✅ Track AI usage statistics
   - ✅ Monitor insight quality

5. **System Settings**
   - ✅ AI configuration management
   - ✅ Location-based rules
   - ✅ Currency settings
   - ✅ Security configurations

---

## 🔄 Application Flow

### 🔐 Authentication Flow

#### Local Login
```
User → Login Form → POST /api/auth/login 
     → Backend validates credentials 
     → Generate JWT token 
     → Return token + user data 
     → Store in localStorage 
     → Redirect to Dashboard
```

#### Google OAuth Login
```
User → Click "Login with Google" 
     → Redirect to /oauth2/authorization/google 
     → Google authentication page 
     → User approves 
     → Google callback to backend 
     → Backend creates/updates user 
     → Generate JWT token 
     → Redirect to frontend with token 
     → Frontend stores token 
     → Redirect to Dashboard
```

#### Token Validation
```
Every Request → Include JWT in Authorization header 
              → JwtAuthenticationFilter validates 
              → Extract user from token 
              → Load user details 
              → Set SecurityContext 
              → Allow request
```

---

### 💸 Expense Flow

```
Client (React)
    ↓
    POST /api/expenses
    ↓
ExpenseController
    ↓
ExpenseService (Business Logic)
    ↓
ExpenseRepository (JPA)
    ↓
MySQL Database
```

**After Expense Added:**
```
ExpenseService.createExpense()
    ↓
BudgetAlertService.checkBudgetAlerts() ← Automatic
    ↓
Calculate current month spending per category
    ↓
Compare with budget limits
    ↓
Generate alerts (WARNING 80%, CRITICAL 90%, EXCEEDED 100%)
    ↓
Store in memory
    ↓
Frontend fetches via GET /api/budgets/alerts
    ↓
Display in Navbar (badge) and Budget page
```

---

### 🤖 AI Advice Flow

**Method 1: Auto-Generated Advice**
```
User → Dashboard/AI Insights page 
     → Clicks "Generate New Advice" 
     → Frontend: POST /api/ai/advice 
     → Backend: AiFinanceService.generateAdvice()
     → Fetch user's expenses from database
     → Build AI prompt with spending data
     → Call Google Gemini API via Spring AI ChatClient
     → Gemini analyzes and returns advice
     → Save insight to database (InsightType.ANALYSIS)
     → Return formatted response
     → Frontend: Display with markdown rendering
     → Add to history sidebar
```

**Method 2: Custom Chat**
```
User → AI Insights page 
     → Click "Ask Custom Question" 
     → Type question: "How can I save ₹5000 this month?"
     → Frontend: POST /api/ai/chat with user question
     → Backend: AiFinanceService.chat()
     → Build AI prompt with user question + context
     → Call Google Gemini API
     → Return personalized response
     → Save insight to database (InsightType.SAVINGS)
     → Frontend: Display with markdown rendering
```

**AI Error Handling:**
```
Gemini API Call
    ↓
Try-Catch Block
    ↓
    ├─ 429 (Quota Exceeded) → "⚠️ AI quota exceeded. Try again later."
    ├─ 401/403 (Auth Error) → "❌ AI service auth failed. Contact support."
    ├─ 5xx (Server Error) → "❌ AI service down. Try again later."
    ├─ Network Error → "❌ Network error. Check connection."
    └─ Any Other Error → Fallback advice with general tips
    ↓
Return graceful error message
    ↓
Frontend displays without crashing
```

---

### 💰 Budget Alert Flow

```
User sets budget:
    POST /api/budgets
    { categoryId: 1, monthlyLimit: 10000 }
    ↓
Stored in database
    ↓
User adds expense:
    POST /api/expenses
    { amount: 500, categoryId: 1 }
    ↓
After save, trigger alert check:
    BudgetAlertService.checkBudgetAlerts(userId)
    ↓
Query: Get all budgets for user
Query: Get current month expenses per category
    ↓
For each category with budget:
    Calculate: spent / limit * 100
    ↓
    Generate alert status:
    • 0-79% → SAFE (green)
    • 80-89% → WARNING (yellow)
    • 90-99% → CRITICAL (orange)
    • 100%+ → EXCEEDED (red, pulsing)
    ↓
Store alerts in memory (or cache)
    ↓
Frontend fetches:
    GET /api/budgets/alerts
    ↓
Display in:
    • Navbar (badge with count of non-SAFE alerts)
    • Budget page (cards with status, progress bars)
    • Toast notification for EXCEEDED status
```

---

## 🗄️ Database Design

### 📊 Entity Relationship Diagram

```
┌─────────────┐         ┌─────────────┐
│    User     │         │  Category   │
├─────────────┤         ├─────────────┤
│ user_id (PK)│         │category_id  │
│ name        │         │name         │
│ email       │◄───┐    │type         │
│ password    │    │    │icon         │
│ city        │    │    │color        │
│ state       │    │    └─────────────┘
│ country     │    │            ▲
│ role        │    │            │
│ created_at  │    │            │
└─────────────┘    │    ┌───────┴──────┐
                   │    │              │
                   │    │              │
        ┌──────────┴────▼──┐  ┌────────▼──────┐
        │     Expense      │  │    Budget     │
        ├──────────────────┤  ├───────────────┤
        │ expense_id (PK)  │  │ budget_id (PK)│
        │ user_id (FK)     │  │ user_id (FK)  │
        │ category_id (FK) │  │ category_id   │
        │ amount           │  │ monthly_limit │
        │ description      │  │ created_at    │
        │ expense_date     │  └───────────────┘
        │ created_at       │
        └──────────────────┘
                   │
        ┌──────────▼────────┐
        │     Income        │
        ├───────────────────┤
        │ income_id (PK)    │
        │ user_id (FK)      │
        │ amount            │
        │ source            │
        │ income_date       │
        │ created_at        │
        └───────────────────┘
                   │
        ┌──────────▼─────────┐
        │   AI_Insight       │
        ├────────────────────┤
        │ insight_id (PK)    │
        │ user_id (FK)       │
        │ insight_text       │
        │ insight_type       │
        │ created_at         │
        └────────────────────┘
```

### 📋 Table Definitions

#### 👤 users
```sql
CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    role ENUM('USER', 'ADMIN') DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

#### 📊 category
```sql
CREATE TABLE category (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    type ENUM('EXPENSE', 'INCOME') NOT NULL,
    icon VARCHAR(10),
    color VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Preloaded categories:
-- EXPENSE: Food, Transport, Shopping, Entertainment, Health, Education, Bills, Travel, Personal, Subscriptions, Investment, Other
-- INCOME: Salary, Business, Investment, Freelance, Gift, Other
```

#### 💸 expense
```sql
CREATE TABLE expense (
    expense_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    description VARCHAR(255),
    expense_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES category(category_id)
);
```

#### 💰 income
```sql
CREATE TABLE income (
    income_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    source VARCHAR(100) NOT NULL,
    income_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
```

#### 📊 budget
```sql
CREATE TABLE budget (
    budget_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    monthly_limit DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY unique_user_category (user_id, category_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES category(category_id)
);
```

#### 🤖 ai_insight
```sql
CREATE TABLE ai_insight (
    insight_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    insight_text TEXT NOT NULL,
    insight_type ENUM('ANALYSIS', 'SAVINGS', 'BUDGET', 'ALERT') DEFAULT 'ANALYSIS',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);
```

---

## 📂 Package Structure

### Backend (Spring Boot)
```
src/main/java/com/example/demo/
│
├── config/
│   ├── AiConfig.java              # ChatClient bean for Gemini
│   ├── SecurityConfig.java        # Security + JWT + OAuth
│   ├── CategoryDataLoader.java   # Preload categories on startup
│   └── CorsConfig.java            # CORS configuration
│
├── controller/
│   ├── AuthController.java        # Login, Register
│   ├── UserController.java        # User profile, update
│   ├── ExpenseController.java     # Expense CRUD
│   ├── IncomeController.java      # Income CRUD
│   ├── BudgetController.java      # Budget CRUD
│   ├── BudgetAlertController.java # Budget alerts API
│   ├── AiInsightController.java   # AI insights history
│   ├── AiFinanceController.java   # AI advice generation
│   ├── CategoryController.java    # Get categories
│   ├── DashboardController.java   # Dashboard stats
│   └── AdminController.java       # Admin operations
│
├── service/
│   ├── AuthService.java           # Authentication logic
│   ├── UserService.java           # User business logic
│   ├── ExpenseService.java        # Expense logic + triggers
│   ├── IncomeService.java         # Income logic
│   ├── BudgetService.java         # Budget logic
│   ├── BudgetAlertService.java    # Alert generation logic
│   ├── AiFinanceService.java      # Gemini AI integration
│   ├── AiInsightService.java      # Insight persistence
│   ├── CategoryService.java       # Category logic
│   └── DashboardService.java      # Analytics logic
│
├── repository/
│   ├── UserRepository.java
│   ├── ExpenseRepository.java     # Custom queries with @Query
│   ├── IncomeRepository.java
│   ├── BudgetRepository.java
│   ├── AiInsightRepository.java
│   └── CategoryRepository.java
│
├── entity/
│   ├── User.java
│   ├── Expense.java               # @ManyToOne with User, Category
│   ├── Income.java
│   ├── Budget.java
│   ├── AiInsight.java
│   └── Category.java
│
├── dto/
│   ├── LoginRequestDTO.java
│   ├── RegisterRequestDTO.java
│   ├── AuthResponseDTO.java
│   ├── ExpenseRequestDTO.java
│   ├── BudgetRequestDTO.java
│   ├── BudgetAlertDTO.java        # Alert response DTO
│   └── AiInsightResponseDTO.java
│
├── security/
│   ├── JwtAuthenticationFilter.java   # JWT validation
│   ├── JwtTokenProvider.java          # Token generation
│   ├── CustomUserDetailsService.java  # Load user for auth
│   └── OAuth2LoginSuccessHandler.java # Google OAuth callback
│
├── exception/
│   ├── ResourceNotFoundException.java
│   ├── GlobalExceptionHandler.java
│   └── UnauthorizedException.java
│
└── FinanceApplication.java        # Main class
```

### Frontend (React)
```
src/
├── api/
│   ├── axios.js              # Axios instance with base URL
│   ├── auth.api.js           # Login, register APIs
│   ├── expense.api.js        # Expense APIs
│   ├── income.api.js         # Income APIs
│   ├── budget.api.js         # Budget APIs
│   ├── ai.api.js             # AI advice APIs
│   └── admin.api.js          # Admin APIs
│
├── components/
│   ├── common/
│   │   ├── Card.jsx          # Reusable card component
│   │   ├── Button.jsx        # Styled button
│   │   ├── Input.jsx         # Form input
│   │   └── Avatar.jsx        # User avatar
│   │
│   ├── auth/
│   │   ├── Login.jsx         # Login form
│   │   ├── Register.jsx      # Registration form
│   │   └── OAuthSuccess.jsx  # OAuth callback handler
│   │
│   ├── layout/
│   │   ├── Navbar.jsx        # Top navigation (with alerts badge)
│   │   ├── Sidebar.jsx       # Side navigation
│   │   └── Footer.jsx        # Footer
│   │
│   └── charts/
│       ├── LineChart.jsx     # Expense trends
│       ├── PieChart.jsx      # Category breakdown
│       └── BarChart.jsx      # Monthly comparison
│
├── pages/
│   ├── Dashboard.jsx         # Main dashboard
│   ├── Expenses.jsx          # Expense management
│   ├── Income.jsx            # Income management
│   ├── Budget.jsx            # Budget page (with alerts)
│   ├── AIInsights.jsx        # AI advisor (with chat)
│   ├── Analytics.jsx         # Reports and analytics
│   ├── Profile.jsx           # User profile
│   └── admin/
│       ├── AdminDashboard.jsx
│       ├── AdminUsers.jsx
│       └── AdminAnalytics.jsx
│
├── context/
│   ├── AuthContext.jsx       # Auth state management
│   └── ThemeContext.jsx      # Dark/light theme
│
├── hooks/
│   ├── useAuth.js            # Authentication hook
│   └── useDebounce.js        # Debounce hook
│
├── utils/
│   ├── helpers.js            # Utility functions
│   ├── constants.js          # App constants (categories, colors)
│   └── validators.js         # Form validation
│
├── routes/
│   ├── ProtectedRoute.jsx    # User route guard
│   └── AdminRoute.jsx        # Admin route guard
│
├── App.jsx                   # Main app with routes
├── main.jsx                  # Entry point
└── index.css                 # Global styles + Tailwind
```

---

## 🔑 API Endpoints

### 🔐 Authentication
```
POST   /api/auth/register          # Register new user
POST   /api/auth/login             # Login with email/password
GET    /oauth2/authorization/google # Initiate Google OAuth
GET    /login/oauth2/code/google   # Google callback (backend only)
```

### 👤 User
```
GET    /api/users/me               # Get current user
PUT    /api/users/{id}             # Update user profile
DELETE /api/users/{id}             # Delete user (admin)
```

### 💸 Expense
```
GET    /api/expenses                # Get all expenses (paginated)
POST   /api/expenses                # Create expense
PUT    /api/expenses/{id}           # Update expense
DELETE /api/expenses/{id}           # Delete expense
GET    /api/expenses/weekly         # Weekly summary
GET    /api/expenses/monthly        # Monthly summary
GET    /api/expenses/yearly         # Yearly summary
GET    /api/expenses/category/{name} # By category
```

### 💰 Income
```
GET    /api/income                  # Get all income
POST   /api/income                  # Create income
PUT    /api/income/{id}             # Update income
DELETE /api/income/{id}             # Delete income
GET    /api/income/monthly          # Monthly income
```

### 📊 Budget
```
GET    /api/budgets                 # Get all budgets
POST   /api/budgets                 # Create budget
PUT    /api/budgets/{id}            # Update budget
DELETE /api/budgets/{id}            # Delete budget
GET    /api/budgets/alerts          # Get budget alerts ⭐ NEW
GET    /api/budgets/alerts/active   # Get active alerts only ⭐ NEW
```

### 🤖 AI Insights
```
GET    /api/ai/advice               # Generate AI advice ⭐ GEMINI
POST   /api/ai/chat                 # Chat with AI ⭐ NEW
GET    /api/ai-insights             # Get insight history
GET    /api/ai-insights/type/{type} # Filter by type
POST   /api/ai-insights             # Save insight directly
DELETE /api/ai-insights/{id}        # Delete insight
```

### 📊 Dashboard
```
GET    /api/dashboard               # Dashboard stats & charts
GET    /api/dashboard/year/{year}   # Yearly overview
```

### 📂 Category
```
GET    /api/categories              # Get all categories
GET    /api/categories/type/{type}  # By type (EXPENSE/INCOME)
```

### 👨‍💼 Admin
```
POST   /api/admin/auth/login        # Admin login
GET    /api/admin/stats             # System statistics
GET    /api/admin/users             # All users (paginated)
PUT    /api/admin/users/{id}/block  # Block user
GET    /api/admin/analytics         # Expense analytics
GET    /api/admin/insights          # AI insights logs
```

---

## ⚙️ Configuration Files

### application.properties
```properties
# Server
server.port=8086
spring.application.name=AI-Finance-Tracker

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/finance_tracker?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# JWT
jwt.secret=your-secret-key-here-make-it-long-and-random-at-least-256-bits
jwt.expiration=86400000

# Google Gemini AI
spring.ai.vertex.ai.gemini.project-id=dummy-project
spring.ai.vertex.ai.gemini.location=us-central1
spring.ai.vertex.ai.gemini.chat.options.model=gemini-pro
spring.ai.vertex.ai.gemini.chat.options.temperature=0.7
spring.ai.vertex.ai.gemini.chat.options.max-output-tokens=2048

# Google OAuth 2.0
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
spring.security.oauth2.client.registration.google.scope=profile,email
spring.security.oauth2.client.registration.google.redirect-uri=http://localhost:8086/login/oauth2/code/google

# Logging
logging.level.org.springframework.ai=DEBUG
logging.level.com.example.demo.service=DEBUG
```

### Frontend .env
```env
VITE_API_BASE_URL=http://localhost:8086
VITE_GOOGLE_CLIENT_ID=YOUR_GOOGLE_CLIENT_ID
VITE_GOOGLE_REDIRECT_URI=http://localhost:3000/auth/google/callback
```

---

## 🚀 How to Run the Project

### ✅ Prerequisites
```
Java 17+
Node.js 16+
MySQL 8+
Maven 3.6+
Google Gemini API key (optional, has fallback)
Google OAuth credentials (optional for OAuth)
```

### ✅ Backend Setup

**1. Clone repository**
```bash
git clone https://github.com/your-repo/finance-tracker.git
cd finance-tracker/backend
```

**2. Create database**
```sql
CREATE DATABASE finance_tracker;
```

**3. Configure application.properties**
- Add MySQL password
- (Optional) Add Gemini API key
- (Optional) Add Google OAuth credentials

**4. Build and run**
```bash
mvn clean install
mvn spring-boot:run
```

Backend runs on: **http://localhost:8086**

### ✅ Frontend Setup

**1. Navigate to frontend**
```bash
cd ../frontend
```

**2. Install dependencies**
```bash
npm install
```

**3. Install additional packages**
```bash
npm install react-markdown  # For AI response formatting
```

**4. Configure .env**
```env
VITE_API_BASE_URL=http://localhost:8086
```

**5. Start development server**
```bash
npm run dev
```

Frontend runs on: **http://localhost:3000**

### ✅ Production Build

**Backend:**
```bash
mvn clean package
java -jar target/finance-tracker-0.0.1-SNAPSHOT.jar
```

**Frontend:**
```bash
npm run build
# Output in dist/ folder
```

---

## 🧪 Testing

### Sample Login Credentials

**User Account:**
```json
{
  "email": "user@gmail.com",
  "password": "12345"
}
```

**Admin Account:**
```json
{
  "email": "admin@financeai.com",
  "password": "admin123"
}
```

### API Testing with Postman

**Login Request:**
```
POST http://localhost:8086/api/auth/login
Content-Type: application/json

{
  "email": "user@gmail.com",
  "password": "12345"
}
```

**Add Expense (with JWT):**
```
POST http://localhost:8086/api/expenses
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
  "amount": 500,
  "categoryId": 1,
  "description": "Lunch at restaurant",
  "expenseDate": "2026-02-19"
}
```

**Get Budget Alerts:**
```
GET http://localhost:8086/api/budgets/alerts
Authorization: Bearer YOUR_JWT_TOKEN
```

**Generate AI Advice:**
```
GET http://localhost:8086/api/ai/advice
Authorization: Bearer YOUR_JWT_TOKEN
```

**Chat with AI:**
```
POST http://localhost:8086/api/ai/chat
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: text/plain

How can I save ₹5000 this month?
```

---

## 💡 Key Features Explained

### 1. Budget Alert System

**How it works:**
1. User sets monthly budget: ₹10,000 for Food category
2. User adds expenses throughout the month
3. After each expense, system calculates: `(totalSpent / budget) * 100`
4. Based on percentage, alert status is determined:
   - **0-79%**: ✅ SAFE (green)
   - **80-89%**: ⚠️ WARNING (yellow)
   - **90-99%**: 🔴 CRITICAL (orange)
   - **100%+**: 🚨 EXCEEDED (red, pulsing animation)
5. Alerts displayed in:
   - Navbar (badge with count)
   - Budget page (detailed cards)
   - Toast notifications (for EXCEEDED)

**Example Alert:**
```json
{
  "categoryId": 1,
  "categoryName": "Food & Dining",
  "limit": 10000,
  "spent": 12500,
  "percentage": 125.0,
  "status": "EXCEEDED",
  "message": "Budget exceeded! Spent ₹12,500 of ₹10,000 (125.0%)"
}
```

### 2. AI Financial Advisor (Gemini)

**Features:**
- **Auto-generated advice** based on user's spending patterns
- **Custom chat** - Ask any finance question
- **Context-aware** - Knows user's location and spending data
- **Markdown formatting** - Bold text, bullet points, numbered lists
- **History tracking** - All insights saved and retrievable
- **Error handling** - Graceful fallbacks when AI is unavailable

**Example Prompts:**
- "Analyze my spending and suggest cost-cutting areas"
- "How can I save ₹10,000 this month?"
- "Create a budget plan for someone earning ₹50,000/month"
- "I'm overspending on food. What should I do?"

**AI Response Format:**
```markdown
# 📊 Actionable Financial Insight for DPL Residents

## 1. **Budgeting**
Given your essential expenses of ₹37,000, aim to keep total needs below 50% of your income.

## 2. **Cost-Cutting Focus**
- **Grocery Savings**: Buy in bulk for staples like rice and lentils at local wholesale markets.
- **Energy Efficiency**: Replace traditional bulbs with LED ones.

## 3. **Expense Tracking**
Use a budgeting app or simple Excel sheet to track daily expenses.

## 4. **Debt Caution**
Avoid personal loans unless absolutely necessary.
```

### 3. Google OAuth Integration

**User Experience:**
1. User clicks "Login with Google" button
2. Redirected to Google login page
3. Approves app permissions
4. Google redirects back to backend with auth code
5. Backend exchanges code for user info
6. Creates or updates user in database
7. Generates JWT token
8. Redirects to frontend with token
9. Frontend stores token and user data
10. User lands on dashboard

**Security:**
- OAuth tokens never exposed to frontend
- Backend handles all OAuth communication
- JWT generated for subsequent requests
- Token includes user email and role

### 4. Real-time Analytics Dashboard

**Metrics displayed:**
- Total Income (current month/year)
- Total Expenses (current month/year)
- Savings = Income - Expenses
- Category-wise breakdown (pie chart)
- Monthly trends (line chart)
- Budget vs Actual comparison
- Top 5 expenses (list)

**Charts:**
- **Line Chart**: Monthly income vs expenses
- **Pie Chart**: Category-wise expense distribution
- **Bar Chart**: Budget adherence by category
- **Progress Bars**: Budget utilization percentage

---

## 🧠 Interview Value

### Technical Skills Demonstrated
✅ **Full-Stack Development** - React + Spring Boot integration  
✅ **AI Integration** - Google Gemini API via Spring AI  
✅ **Authentication** - JWT + OAuth 2.0 (Google)  
✅ **Database Design** - Normalized schema with relationships  
✅ **REST API Design** - RESTful principles, proper status codes  
✅ **Security** - Token-based auth, password hashing, CORS  
✅ **State Management** - React Hooks, Context API  
✅ **Error Handling** - Graceful degradation, fallback mechanisms  
✅ **Real-time Features** - Budget alerts, notifications  
✅ **Data Visualization** - Charts with Recharts  
✅ **Clean Architecture** - Layered structure, separation of concerns  
✅ **API Integration** - External services (Gemini, Google OAuth)

### Problem-Solving Demonstrated
✅ **Real-world problem** - Personal finance management  
✅ **Budget monitoring** - Automated alerts and warnings  
✅ **AI recommendations** - Context-aware financial advice  
✅ **User experience** - Smooth OAuth flow, responsive design  
✅ **Performance optimization** - Lazy loading, pagination  
✅ **Error resilience** - AI fallbacks, network error handling

### Interview Questions You Can Answer

**Q: How does your budget alert system work?**  
A: After each expense is added, the system calculates the total spending for that category in the current month, compares it with the user's set budget limit, computes the percentage, and generates alerts based on thresholds (80% WARNING, 90% CRITICAL, 100% EXCEEDED). These alerts are displayed in real-time in the navbar and budget page.

**Q: How did you integrate Google Gemini AI?**  
A: I used Spring AI framework's ChatClient to interact with Google Gemini. The service builds context-aware prompts including user's spending data and location, sends them to Gemini API, receives markdown-formatted advice, and saves insights to the database. I implemented robust error handling with fallback advice for quota/network issues.

**Q: Explain your authentication flow.**  
A: For local auth, credentials are validated, BCrypt checks password, JWT token is generated with user email and role, and returned to frontend. For OAuth, backend initiates Google OAuth flow, receives user info from Google, creates/updates user, generates JWT, and redirects to frontend with token. All subsequent requests include JWT in Authorization header, validated by JwtAuthenticationFilter.

**Q: How do you handle AI service failures?**  
A: I implemented a try-catch block with specific error handling for different scenarios: 429 (quota exceeded) shows retry message, 401/403 (auth errors) suggests contacting support, 5xx (server errors) indicates temporary downtime, network errors show connection issues, and any unexpected error triggers fallback advice with general financial tips. This ensures the application never crashes due to AI failures.

**Q: What database optimizations did you implement?**  
A: Used @Transactional for data consistency, JOIN FETCH in queries to avoid N+1 problem, added composite unique keys (user_id + category_id for budgets), created indexes on frequently queried fields (user_id, expense_date, category_id), and implemented pagination for large datasets.

---

## 📌 Future Enhancements

### Planned Features
- [ ] Expense prediction using ML (LSTM/Prophet)
- [ ] Bank account integration (Plaid API)
- [ ] Email notifications for budget alerts
- [ ] PDF report exports
- [ ] Recurring expense tracking
- [ ] Shared budgets for families
- [ ] Mobile app (React Native)
- [ ] Investment portfolio tracking
- [ ] Bill reminders and autopay
- [ ] Financial goal setting and tracking
- [ ] Multi-currency support
- [ ] Voice-based expense entry
- [ ] Receipt scanning with OCR
- [ ] Expense categorization ML
- [ ] Chatbot for 24/7 advice

### Technical Improvements
- [ ] Redis caching for frequent queries
- [ ] GraphQL API option
- [ ] WebSocket for real-time updates
- [ ] Docker containerization
- [ ] CI/CD pipeline
- [ ] Unit and integration tests
- [ ] API rate limiting
- [ ] Logging and monitoring (ELK stack)
- [ ] Database replication
- [ ] CDN for static assets

---

## 👨‍💻 Contributors

**Developer:** Devendra Solanki  
**Role:** Java Full Stack Developer  
**Tech Stack:** Java, Spring Boot, Spring Security, JWT, Spring AI, Hibernate, JPA, REST APIs, React, MySQL, Google Gemini AI  
**Email:** devendrasolanki1485@gmail.com  
**Phone:** +91 7828199107  
**Location:** Indore, Madhya Pradesh, India  
**LinkedIn:** [linkedin.com/in/devendrasolanki03](https://linkedin.com/in/devendrasolanki03)  
**GitHub:** [github.com/Devendrasolanki03](https://github.com/Devendrasolanki03)  
**Education:** B.Tech - Computer Science, Malwa Institute of Science and Technology (2021-2024) | CGPA: 7.7/10

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- **Spring AI Team** - For excellent AI integration framework
- **Google Gemini** - For powerful AI capabilities
- **Recharts** - For beautiful data visualizations
- **Tailwind CSS** - For rapid UI development
- **React Community** - For amazing libraries and tools

---

## 📞 Support

For any questions or issues:
- **Email:** devendrasolanki1485@gmail.com
- **Phone:** +91 7828199107
- **GitHub:** [github.com/Devendrasolanki03](https://github.com/Devendrasolanki03)
- **LinkedIn:** [linkedin.com/in/devendrasolanki03](https://linkedin.com/in/devendrasolanki03)
- **GitHub Issues:** [Create an issue](https://github.com/Devendrasolanki03/finance-tracker/issues)

---

**🎉 Thank you for checking out this project!**

Made with ❤️ by **Devendra Solanki** | Java Full Stack Developer | Indore, India