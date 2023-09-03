import Link from "next/link";

export default function AccountSwitch(props: any) {
  return (
    <div className="mt-8 text-md flex space-x-2">
      <div className="text-theme-grey">{props.message}</div>
      <Link
        href={props.link}
        className="text-theme-blue hover:text-theme-light-blue"
      >
        {props.prompt}
      </Link>
    </div>
  );
}
