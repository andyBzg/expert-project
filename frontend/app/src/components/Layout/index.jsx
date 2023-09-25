import React from "react";
import NavMenu from "../NavMenu";
import { Outlet } from "react-router-dom";
import Footer from "../Footer";

//Создаём Layout с Outlet

export default function Layout() {
  return (
    <div>
      <NavMenu />
      <Outlet />
      <Footer />
    </div>
  );
}
