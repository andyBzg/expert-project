import React from "react";
import s from "./style.module.css";

export default function Container({ children, className }) {
  return <div className={[s.container, className].join(" ")}>{children}</div>;
}
