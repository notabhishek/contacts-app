import React, { useEffect, useState } from "react";
import {
  styled,
  useTheme,
  ThemeProvider,
  createTheme,
} from "@mui/material/styles";
import Box from "@mui/material/Box";
import CssBaseline from "@mui/material/CssBaseline";
import AppBarComponent from "../../Components/Home/AppMenuComponent";
import DrawerPanel, { DrawerHeader } from "../../Components/Home/DrawerPanel";
import { Outlet } from "react-router-dom";
import { LIGHT_THEME , DARK_THEME } from "../../Utils/themes";
import { useAppConsumer } from "../../Utils/AppContext/AppContext";

export default function HomeView() {
  const {themeContext} = useAppConsumer();
  const [theme ,setTheme] = themeContext

  const [open, setOpen] = useState(true);

  const handleDrawerOpen = () => {
    setOpen(true);
  };

  const handleDrawerClose = () => {
    setOpen(false);
  };

  return (
    <ThemeProvider theme={theme === 'darkTheme' ? DARK_THEME : LIGHT_THEME}>
      <Box sx={{ display: "flex" }}>
        <CssBaseline />
        <AppBarComponent
          open={open}
          handleDrawerOpen={handleDrawerOpen}
        />
        <DrawerPanel
          theme={theme}
          open={open}
          handleDrawerClose={handleDrawerClose}
        />
        <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
          <DrawerHeader />
          <Box sx={{ width: "100%", typography: "body1" }}>
            <Outlet/>
          </Box>
        </Box>
      </Box>
    </ThemeProvider>
  );
}
