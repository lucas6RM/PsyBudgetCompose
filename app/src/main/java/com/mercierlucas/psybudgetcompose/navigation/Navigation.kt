package com.mercierlucas.psybudgetcompose.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mercierlucas.psybudgetcompose.ui.auth.login.LoginScreen
import com.mercierlucas.psybudgetcompose.ui.auth.login.LoginViewModel
import com.mercierlucas.psybudgetcompose.ui.mainmenu.MainMenuScreen
import com.mercierlucas.psybudgetcompose.ui.mainmenu.MainMenuViewModel
import com.mercierlucas.psybudgetcompose.ui.patients.all.AllPatientsScreen
import com.mercierlucas.psybudgetcompose.ui.patients.all.AllPatientsViewModel
import com.mercierlucas.psybudgetcompose.ui.patients.create.CreatePatientScreen
import com.mercierlucas.psybudgetcompose.ui.patients.create.CreatePatientViewModel
import com.mercierlucas.psybudgetcompose.ui.patients.one.PatientByIdScreen
import com.mercierlucas.psybudgetcompose.ui.patients.one.PatientByIdViewModel
import com.mercierlucas.psybudgetcompose.ui.auth.register.RegisterScreen
import com.mercierlucas.psybudgetcompose.ui.auth.register.RegisterViewModel
import com.mercierlucas.psybudgetcompose.ui.sessions.create.CreateSessionScreen
import com.mercierlucas.psybudgetcompose.ui.sessions.create.CreateSessionViewModel
import com.mercierlucas.psybudgetcompose.ui.sessions.daily.DailySessionViewModel
import com.mercierlucas.psybudgetcompose.ui.sessions.daily.DailySessionsScreen
import com.mercierlucas.psybudgetcompose.ui.sessions.details.DetailsSessionScreen
import com.mercierlucas.psybudgetcompose.ui.sessions.details.DetailsSessionViewModel
import com.mercierlucas.psybudgetcompose.ui.sessions.menu.MenuSessionScreen
import com.mercierlucas.psybudgetcompose.ui.settings.SettingsMenuViewModel
import com.mercierlucas.psybudgetcompose.ui.settings.SettingsMenuScreen
import com.mercierlucas.psybudgetcompose.ui.splash.SplashScreen
import com.mercierlucas.psybudgetcompose.ui.splash.SplashViewModel
import com.mercierlucas.psybudgetcompose.ui.transactions.incomes.IncomesByMonthScreen
import com.mercierlucas.psybudgetcompose.ui.transactions.incomes.IncomesByMonthViewModel
import com.mercierlucas.psybudgetcompose.ui.transactions.menu.MenuTransactionScreen
import com.mercierlucas.psybudgetcompose.ui.transactions.validate.ValidatePaymentsScreen
import com.mercierlucas.psybudgetcompose.ui.transactions.validate.ValidatePaymentsViewModel

sealed class Screen(val route : String){
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object MainMenu : Screen("main_menu" )
    object AllPatients : Screen("all_patients" )
    object CreatePatient : Screen("create_patient" )
    object PatientById : Screen("patient_by_id" )
    object CreateSession : Screen("create_session" )
    object SessionMenu : Screen("session_menu" )
    object DailySessions : Screen("daily_sessions" )
    object DetailsSession : Screen("details_session")
    object TransactionsMenu : Screen("transactions_menu" )
    object IncomesByMonth : Screen("incomes_by_month" )
    object ValidatePayments : Screen("validate_payments" )
    object SettingsMenu : Screen("settings_menu")


}

@Composable
fun MyNavigation(navController: NavHostController = rememberNavController()){

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ){
        composable(Screen.Splash.route) {
            val splashViewModel: SplashViewModel = hiltViewModel()
            SplashScreen(navController, splashViewModel)
        }

        composable(Screen.Login.route) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(navController, loginViewModel)
        }

        composable(Screen.Register.route) {
            val registerViewModel: RegisterViewModel = hiltViewModel()
            RegisterScreen(navController, registerViewModel)
        }

        composable(Screen.MainMenu.route) {
            val mainMenuViewModel: MainMenuViewModel = hiltViewModel()
            MainMenuScreen(navController, mainMenuViewModel)
        }

        composable(Screen.AllPatients.route) {
            val allPatientsViewModel: AllPatientsViewModel = hiltViewModel()
            AllPatientsScreen(navController, allPatientsViewModel)
        }

        composable(Screen.CreatePatient.route) {
            val createPatientViewModel: CreatePatientViewModel = hiltViewModel()
            CreatePatientScreen(navController, createPatientViewModel)
        }

        composable(
            Screen.PatientById.route + "/{id}",
            arguments = listOf(navArgument(name = "id"){
                type = NavType.LongType
            })
        ){navBackStackEntry ->
            val idPatientSelected = navBackStackEntry.arguments?.getLong("id") ?:0L
            val patientByIdViewModel : PatientByIdViewModel = hiltViewModel()
            PatientByIdScreen(navController, patientByIdViewModel, idPatientSelected)
        }

        composable(Screen.CreateSession.route) {
            val createSessionViewModel: CreateSessionViewModel = hiltViewModel()
            CreateSessionScreen(navController, createSessionViewModel)
        }

        composable(Screen.SessionMenu.route) {
            MenuSessionScreen(navController)
        }

        composable(Screen.DailySessions.route) {
            val dailySessionViewModel: DailySessionViewModel = hiltViewModel()
            DailySessionsScreen(navController, dailySessionViewModel)
        }

        composable(
            Screen.DetailsSession.route + "/{sessionId}",
            arguments = listOf(navArgument(name = "sessionId"){
                type = NavType.LongType
            })
        ) {navBackStackEntry ->
            val idSessionSelected = navBackStackEntry.arguments?.getLong("sessionId") ?: 0L
            val detailsSessionViewModel :DetailsSessionViewModel = hiltViewModel()
            DetailsSessionScreen(navController,detailsSessionViewModel,idSessionSelected)
        }

        composable(Screen.TransactionsMenu.route) {
            MenuTransactionScreen(navController)
        }

        composable(Screen.IncomesByMonth.route){
            val incomesByMonthViewModel: IncomesByMonthViewModel = hiltViewModel()
            IncomesByMonthScreen(navController, incomesByMonthViewModel)
        }

        composable(Screen.ValidatePayments.route){
            val validatePaymentsViewModel : ValidatePaymentsViewModel = hiltViewModel()
            ValidatePaymentsScreen(navController, validatePaymentsViewModel)
        }

        composable(Screen.SettingsMenu.route){
            val settingsMenuViewModel : SettingsMenuViewModel = hiltViewModel()
            SettingsMenuScreen(navController, settingsMenuViewModel)
        }
    }
}

