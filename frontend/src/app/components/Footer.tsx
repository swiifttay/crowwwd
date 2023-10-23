export default function Footer() {
  return (
    <footer className="grid grid-cols-12 py-10 items-center h-64 bg-footer w-full xl:rounded-3xl xl:my-6">
      <div className="col-span-full md:col-start-2 md:col-span-3 md:border-e border-slate-600 text-center md:text-left text-sm md:text-xs">
        <h1 className="my-2 font-bold text-2xl text-indigo-100 md:text-xl">
          CROWWWD.
        </h1>
        {[
          ["Dexter", "https://www.linkedin.com/in/dexterlimhj/"],
          ["Joel", "https://www.youtube.com/watch?v=a3Z7zEc7AXQ"],
          ["Yu Ting", "https://www.linkedin.com/in/yuting-huang-"],
          ["Eugene", "https://www.linkedin.com/in/eugene-lian-a72ab8223/"],
          ["Si Yu", "https://www.linkedin.com/in/si-yu-tay/"],
          ["Jerry", "https://www.linkedin.com/in/jeremiah-rabino-411b9626b/"],
        ].map(([name, linkedin]) => {
          return (
            <a href={`${linkedin}`}>
              <h3 key="name" className="mb-1 font-light text-slate-400">
                {name}
              </h3>
            </a>
          );
        })}
      </div>
      <div className="hidden md:flex md:col-span-8 justify-center items-center text-slate-400">
        <div className="flex w-full justify-around">
          <div className="text-center">
            <h1 className="text-5xl font-bold text-indigo-100">10%</h1>
            <h3 className="font-light">Luck</h3>
          </div>
          <div className="text-center">
            <h1 className="text-5xl font-bold text-indigo-100">20%</h1>
            <h3 className="font-light">Skill</h3>
          </div>
          <div className="text-center">
            <h1 className="text-5xl font-bold text-indigo-100">15%</h1>
            <h3 className="font-light">
              Concentrated
              <br />
              Power of Will
            </h3>
          </div>
        </div>
      </div>
    </footer>
  );
}
