export default function Footer() {
  return (
    <footer className="grid grid-cols-12 py-16 h-80 bg-footer w-full">
      <div className=" col-start-2 col-span-2 border-e border-slate-600">
        <h1 className="my-2 font-bold text-xl text-indigo-100">CROWWWD.</h1>
        {["Dexter", "Joel", "Yuting", "Eugene", "Si Yu", "Jerry"].map(
          (name) => {
            return (
              <h3 key="name" className="mb-1 text-xs font-light text-slate-400">
                {name}
              </h3>
            );
          },
        )}
      </div>
      <div className="col-span-6 border-e border-slate-500"></div>
    </footer>
  );
}
