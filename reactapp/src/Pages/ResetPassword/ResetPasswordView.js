import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import { Link } from "@mui/material";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { ThemeProvider } from "@mui/material/styles";
import Alert from "@mui/material/Alert";
import Snackbar from "@mui/material/Snackbar";

export default function ResetPasswordView({
  handleSubmit,
  currentTheme,
  alertOpen,
  setAlertOpen,
  errorMessage,
  handleGenResetToken,
}) {
  return (
    <ThemeProvider theme={currentTheme}>
      <Container component="main" maxWidth="xs" sx={{ marginBottom: 10 }}>
        <CssBaseline />
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography component="h1" variant="h5">
            Reset Password
          </Typography>
          <Box
            component="form"
            onSubmit={handleGenResetToken}
            noValidate
            sx={{ mt: 1 }}
          >
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="Email Address"
              name="email"
              autoComplete="email"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >Generate Reset Token</Button>
          </Box>
        </Box>
        <Box
          sx={{
            marginTop: 8,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Box
            component="form"
            onSubmit={handleSubmit}
            noValidate
            sx={{ mt: 1 }}
          >
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="Email Address"
              name="email"
              autoComplete="email"
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="New Password"
              type="password"
              id="password"
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="confirm password"
              label="Confirm New Password"
              type="password"
              id="confirm password"
            />
            <TextField
              margin="normal"
              fullWidth
              id="resetToken"
              label="Password Reset Token"
              name="resetToken"
              autoComplete="resetToken"
            />
            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{ mt: 3, mb: 2 }}
            >
              Reset Password
            </Button>
            <Grid container justifyContent={"center"}>
              <Grid item>
                <Link href="/login" variant="body2">
                  {"Remember your password? Login"}
                </Link>
              </Grid>
            </Grid>
          </Box>
        </Box>
        <Snackbar
          open={alertOpen}
          autoHideDuration={6000}
          onClose={() => setAlertOpen(false)}
        >
          <Alert
            onClose={() => setAlertOpen(false)}
            severity="error"
            sx={{ width: "100%" }}
          >
            {errorMessage}
          </Alert>
        </Snackbar>
      </Container>
    </ThemeProvider>
  );
}
