import { createTheme } from "@mui/material";

export const COLORS = ["#eac435","#345995","#03cea4","#fb4d3d","#ca1551" ,"#5f0f40","#9a031e","#fb8b24","#e36414","#0f4c5c","#ea638c","#002a22","#def6ca","#f497da","#f679e5"]

export const LIGHT_THEME = createTheme();
export const DARK_THEME = createTheme({
    palette: {
      mode: "dark",
    },
  });