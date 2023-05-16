export default function useTransition_(location) {
  const routing_animation = () => {
    if (location && location.pathname.includes("home"))
      return "translateX(100px)";
    if (location && location.pathname.includes("login"))
      return "translateX(-100px)";
  };
  return {
    from: {
      opacity: 0,
      transform: routing_animation(),
    },
    enter: {
      opacity: 1,
      transform: "translateX(0)",
    },
    leave: {
      opacity: 0,
      display: "none",
    },
  };
}
