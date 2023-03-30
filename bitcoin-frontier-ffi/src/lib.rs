uniffi::include_scaffolding!("bitcoinfrontier");

pub fn welcome() -> String {
    String::from("Welcome to the frontier")
}

// #[derive(Debug)]
// pub enum CalculatorError {
//     DivisionBy0
// }
//
// impl std::fmt::Display for CalculatorError {
//     fn fmt(&self, f: &mut std::fmt::Formatter<'_>) -> std::fmt::Result {
//         write!(f, "{:?}", self)
//     }
// }
//
// impl std::error::Error for CalculatorError {}
