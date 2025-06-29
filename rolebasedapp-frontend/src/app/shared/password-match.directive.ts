import { AbstractControl, ValidationErrors, ValidatorFn } from "@angular/forms";

/**
 * Reusable password match validator
 * @param passwordFieldName The field name of the password
 * @param confirmPasswordFieldName The field name of the confirm password
 */
export function passwordMatchValidator(passwordFieldName: string, confirmPasswordFieldName: string): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const password = control.get(passwordFieldName);
    const confirmPassword = control.get(confirmPasswordFieldName);

    if (!password || !confirmPassword) {
      return null;
    }

    return password.value === confirmPassword.value ? null : { passwordMismatch: true };
  };
}